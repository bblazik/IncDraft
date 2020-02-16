package com.koktajlbar.incognitobook.datasources

import androidx.lifecycle.LiveData
import com.koktajlbar.incognitobook.AppDatabase
import com.koktajlbar.incognitobook.model.Cocktail
import java.util.*
import javax.inject.Inject

class CocktailLocalDataSource @Inject constructor(private val database: AppDatabase) : CocktailDataSource {
    private val cocktailDao = database.cocktailDao()

    override suspend fun allCocktails(): LiveData<MutableList<Cocktail>> {
        var all = cocktailDao.all.value
        return cocktailDao.all
    }

    override suspend fun getCocktail(uuid: UUID): LiveData<Cocktail> {
        return cocktailDao.find_by_uuid(uuid)
    }
}