package com.koktajlbar.incognitobook;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import android.content.Context;


import com.koktajlbar.incognitobook.dao.CocktailDao;
import com.koktajlbar.incognitobook.dao.GuestCocktailJoinDao;
import com.koktajlbar.incognitobook.dao.GuestDao;
import com.koktajlbar.incognitobook.dao.GuestWithCocktailsDao;
import com.koktajlbar.incognitobook.model.Cocktail;
import com.koktajlbar.incognitobook.model.Guest;
import com.koktajlbar.incognitobook.model.GuestCocktailJoin;
import com.koktajlbar.incognitobook.utils.UUIDTypeConverter;

@Database(entities = {Guest.class, Cocktail.class, GuestCocktailJoin.class}, version = 23, exportSchema = false)
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
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
