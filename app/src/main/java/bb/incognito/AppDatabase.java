package bb.incognito;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import bb.incognito.dao.CocktailDao;
import bb.incognito.dao.GuestDao;
import bb.incognito.dao.GuestWithCocktailsDao;
import bb.incognito.model.Cocktail;
import bb.incognito.model.Guest;

@Database(entities = {Guest.class, Cocktail.class}, version = 8, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GuestDao guestDao();
    public abstract CocktailDao cocktailDao();
    public abstract GuestWithCocktailsDao guestWithCocktailsDao();

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

        PopulateDbAsync(AppDatabase db) {
            guestDao = db.guestDao();
            cocktailDao = db.cocktailDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            guestDao.deleteAll();
            Guest guest = new Guest("Dupa 1");
            guest.setId((int) guestDao.insertGuest(guest));
            guest = new Guest("Dupa 2");
            guest.setId((int) guestDao.insertGuest(guest));

            cocktailDao.deleteAll();
            Cocktail cocktail = new Cocktail("Mohito");
            cocktail.setId((int) cocktailDao.insertCocktail(cocktail));
            return null;
        }
    }
}
