package com.koktajlbar.incognitobook.datasources

import androidx.lifecycle.LiveData
import com.koktajlbar.incognitobook.model.Cocktail
import java.util.*

interface CocktailDataSource {
    suspend fun allCocktails(): LiveData<MutableList<Cocktail>>
    suspend fun getCocktail(uuid: UUID): LiveData<Cocktail>
}