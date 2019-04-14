package bb.incognito;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.api.Api;

import java.io.IOException;
import java.util.List;

import bb.incognito.dao.CocktailDao;
import bb.incognito.dao.GuestCocktailJoinDao;
import bb.incognito.dao.GuestDao;
import bb.incognito.dao.GuestWithCocktailsDao;
import bb.incognito.model.Cocktail;
import bb.incognito.model.Guest;
import bb.incognito.model.GuestCocktailJoin;
import bb.incognito.utils.UUIDTypeConverter;
import okhttp3.ResponseBody;
import retrofit2.Response;

@Database(entities = {Guest.class, Cocktail.class, GuestCocktailJoin.class}, version = 17, exportSchema = false)
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
            guestDao.deleteAll();
            Guest guest = new Guest("Dupa 1");
            guestDao.insertGuest(guest);
            guest = new Guest("Dupa 2");
            guestDao.insertGuest(guest);


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
