package bb.incognito;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import bb.incognito.dao.CocktailDao;
import bb.incognito.dao.GuestCocktailJoinDao;
import bb.incognito.dao.GuestDao;
import bb.incognito.dao.GuestWithCocktailsDao;
import bb.incognito.model.Cocktail;
import bb.incognito.model.Guest;
import bb.incognito.model.GuestCocktailJoin;

@Database(entities = {Guest.class, Cocktail.class, GuestCocktailJoin.class}, version = 9, exportSchema = false)
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

            guestCocktailJoinDao.deleteAll();
            cocktailDao.deleteAll();
            guestDao.deleteAll();
            Guest guest = new Guest("Dupa 1");
            guest.setId((int) guestDao.insertGuest(guest));
            guest = new Guest("Dupa 2");
            guest.setId((int) guestDao.insertGuest(guest));


            Cocktail cocktail = new Cocktail("Mohito");
            cocktail.setId((int) cocktailDao.insertCocktail(cocktail));

            guestCocktailJoinDao.insert( new GuestCocktailJoin(guest.getId(), cocktail.getId()) );

            return null;
        }
    }
}
