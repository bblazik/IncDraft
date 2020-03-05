package com.koktajlbar.incognitobook

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.koktajlbar.incognitobook.dao.CocktailDao
import com.koktajlbar.incognitobook.model.Cocktail
import com.koktajlbar.incognitobook.utils.UUIDTypeConverter

@Database(entities = [Cocktail::class], version = 23, exportSchema = false)
@TypeConverters(UUIDTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
}