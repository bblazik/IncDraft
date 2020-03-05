package com.koktajlbar.incognitobook.modules

import android.content.Context
import androidx.room.Room
import com.koktajlbar.incognitobook.AppDatabase
import com.koktajlbar.incognitobook.CocktailApi
import com.koktajlbar.incognitobook.datasources.CocktailLocalDataSource
import com.koktajlbar.incognitobook.datasources.CocktailRemoteDataSource
import com.koktajlbar.incognitobook.repositories.DefaultCocktailRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val koinDatabaseModule =  module {
    single { provideDefaultCocktailRepository(get(), get()) }
    single { provideDatabase(androidContext()) }
    single { provideCocktailsLocalDataSource(get())}
    single { provideCocktailsRemoteDataSource(get()) }
}

internal fun provideDefaultCocktailRepository(
        cocktailsLocalDataSource: CocktailLocalDataSource,
        cocktailRemoteDataSource: CocktailRemoteDataSource
) : DefaultCocktailRepository {
    return DefaultCocktailRepository(cocktailsLocalDataSource, cocktailRemoteDataSource)
}

internal fun provideCocktailsRemoteDataSource(cocktailApi: CocktailApi) : CocktailRemoteDataSource {
    return CocktailRemoteDataSource(cocktailApi)
}

internal fun provideCocktailsLocalDataSource(database: AppDatabase): CocktailLocalDataSource {
    return CocktailLocalDataSource(database)
}

internal fun provideDatabase(context: Context): AppDatabase {
    return Room
            .databaseBuilder(context.applicationContext, AppDatabase::class.java, "bar_book.db")
            .fallbackToDestructiveMigration()
            .build()
}