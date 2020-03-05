package com.koktajlbar.incognitobook.datasources

import androidx.lifecycle.LiveData
import com.koktajlbar.incognitobook.AppDatabase
import com.koktajlbar.incognitobook.model.Cocktail
import java.util.*

class CocktailLocalDataSource(database: AppDatabase) : CocktailDataSource {
    private val cocktailDao = database.cocktailDao()

    override suspend fun allCocktails(): LiveData<MutableList<Cocktail>> {
        return cocktailDao.all
    }

    override suspend fun getCocktail(uuid: UUID): LiveData<Cocktail> {
        return cocktailDao.findByUuid(uuid)
    }
}