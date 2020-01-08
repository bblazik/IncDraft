package com.koktajlbar.incognitobook;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import android.widget.Toast;

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

@Database(entities = {Guest.class, Cocktail.class, GuestCocktailJoin.class}, version = 21, exportSchema = false)
@TypeConverters({UUIDTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract GuestDao guestDao();
    public abstract CocktailDao cocktailDao();
    public abstract GuestWithCocktailsDao guestWithCocktailsDao();
    public abstract GuestCocktailJoinDao guestCocktailJoinDao();

    private static AppDatabase INSTANCE;
    private static Context mcontext;

    public static AppDatabase getDatabase(final Context context) {
        mcontext = context;
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

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Boolean> {
        private final GuestDao guestDao;
        private final CocktailDao cocktailDao;
        private final GuestCocktailJoinDao guestCocktailJoinDao;

        PopulateDbAsync(AppDatabase db) {
            guestDao = db.guestDao();
            cocktailDao = db.cocktailDao();
            guestCocktailJoinDao = db.guestCocktailJoinDao();
        }

        @Override
        protected Boolean doInBackground(final Void... params) {

            List<Cocktail> cocktailList = null;
            Service service = API.getClient();

            guestCocktailJoinDao.deleteAll();
            cocktailDao.deleteAll();

            try {
                cocktailList = service.getCocktails().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            for(Cocktail cocktail: cocktailList) {
                cocktailDao.insertCocktail(cocktail);
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean executedSuccesfully) {
            super.onPostExecute(executedSuccesfully);
            if(!executedSuccesfully)
                Toast.makeText(mcontext, mcontext.getText(R.string.InternetError), Toast.LENGTH_LONG).show();
        }
    }
}
