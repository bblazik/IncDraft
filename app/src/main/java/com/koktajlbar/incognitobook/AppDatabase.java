package com.koktajlbar.incognitobook;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.List;

import com.koktajlbar.incognitobook.dao.CocktailDao;
import com.koktajlbar.incognitobook.dao.GuestCocktailJoinDao;
import com.koktajlbar.incognitobook.dao.GuestDao;
import com.koktajlbar.incognitobook.dao.GuestWithCocktailsDao;
import com.koktajlbar.incognitobook.model.Cocktail;
import com.koktajlbar.incognitobook.model.Guest;
import com.koktajlbar.incognitobook.model.GuestCocktailJoin;
import com.koktajlbar.incognitobook.utils.UUIDTypeConverter;

@Database(entities = {Guest.class, Cocktail.class, GuestCocktailJoin.class}, version = 19, exportSchema = false)
@TypeConverters({UUIDTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract GuestDao guestDao();
    public abstract CocktailDao cocktailDao();
    public abstract GuestWithCocktailsDao guestWithCocktailsDao();
    public abstract GuestCocktailJoinDao guestCocktailJoinDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final GuestDao guestDao;
        private final CocktailDao cocktailDao;
        private final GuestCocktailJoinDao guestCocktailJoinDao;

        PopulateDbAsync(AppDatabase db) {
            guestDao = db.guestDao();
            cocktailDao = db.cocktailDao();
            guestCocktailJoinDao = db.guestCocktailJoinDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            List<Cocktail> cocktailList = null;
            Service service = API.getClient();
            try {
                cocktailList = service.getCocktails().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            guestCocktailJoinDao.deleteAll();
            cocktailDao.deleteAll();
//            guestDao.deleteAll();
//            Guest guest = new Guest("Dupa 1");
//            guestDao.insertGuest(guest);
//            guest = new Guest("Dupa 2");
//            guestDao.insertGuest(guest);


            for(Cocktail cocktail: cocktailList)
            {
                cocktailDao.insertCocktail(cocktail);
            }

//            Cocktail cocktail = new Cocktail("Mohito");
//            cocktailDao.insertCocktail(cocktail);
//
//            cocktail = new Cocktail("Dajkiłe");
//            cocktailDao.insertCocktail(cocktail);
//
//            cocktail = new Cocktail("ManHatAn");
//            cocktailDao.insertCocktail(cocktail);

//            guestCocktailJoinDao.insert( new GuestCocktailJoin(guest.getId(), cocktail.getId()) );

            return null;
        }
    }
}
