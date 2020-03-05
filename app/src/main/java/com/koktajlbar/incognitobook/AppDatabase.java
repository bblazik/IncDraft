package com.koktajlbar.incognitobook;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.koktajlbar.incognitobook.dao.CocktailDao;
import com.koktajlbar.incognitobook.model.Cocktail;
import com.koktajlbar.incognitobook.utils.UUIDTypeConverter;

@Database(entities = {Cocktail.class}, version = 23, exportSchema = false)
@TypeConverters({UUIDTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract CocktailDao cocktailDao();
    private static AppDatabase INSTANCE;
}
