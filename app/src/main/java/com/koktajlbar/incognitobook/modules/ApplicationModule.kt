package com.koktajlbar.incognitobook.modules

import android.content.Context
import androidx.room.Room
import com.koktajlbar.incognitobook.AppDatabase
import com.koktajlbar.incognitobook.datasources.CocktailLocalDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ApplicationModule {
    @Provides
    @Singleton
    fun provideCocktailsLocalDataSource(database: AppDatabase): CocktailLocalDataSource {
        return CocktailLocalDataSource(database)
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room
            .databaseBuilder(context.applicationContext, AppDatabase::class.java, "bar_book.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}