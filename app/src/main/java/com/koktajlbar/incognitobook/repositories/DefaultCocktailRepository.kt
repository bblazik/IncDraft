package com.koktajlbar.incognitobook.repositories

import androidx.lifecycle.LiveData
import com.koktajlbar.incognitobook.datasources.CocktailLocalDataSource
import com.koktajlbar.incognitobook.model.Cocktail
import java.util.*
import javax.inject.Inject

class DefaultCocktailRepository @Inject constructor(
        private val cocktailsLocalDataSource: CocktailLocalDataSource
) : CocktailRepository {

    override suspend fun allCocktails(): LiveData<MutableList<Cocktail>> {
        var dupa = cocktailsLocalDataSource.allCocktails()
        return dupa
    }

    override suspend fun getCocktail(uuid: UUID): LiveData<Cocktail> {
        return cocktailsLocalDataSource.getCocktail(uuid)
    }
}