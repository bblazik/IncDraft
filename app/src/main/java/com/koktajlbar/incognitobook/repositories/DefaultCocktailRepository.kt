package com.koktajlbar.incognitobook.repositories

import androidx.lifecycle.LiveData
import com.koktajlbar.incognitobook.datasources.CocktailLocalDataSource
import com.koktajlbar.incognitobook.datasources.CocktailRemoteDataSource
import com.koktajlbar.incognitobook.model.Cocktail
import java.util.*

class DefaultCocktailRepository(
        private val cocktailsLocalDataSource: CocktailLocalDataSource,
        private val cocktailsRemoteDataSource: CocktailRemoteDataSource
) : CocktailRepository {

    override suspend fun allCocktails(): LiveData<MutableList<Cocktail>> {
        return cocktailsRemoteDataSource.allCocktails()
    }

    override suspend fun getCocktail(uuid: UUID): LiveData<Cocktail> {
        return cocktailsRemoteDataSource.getCocktail(uuid)
    }
}