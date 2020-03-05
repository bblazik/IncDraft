package com.koktajlbar.incognitobook.repositories

import androidx.lifecycle.LiveData
import com.koktajlbar.incognitobook.model.Cocktail
import java.util.*

interface CocktailRepository {
    suspend fun allCocktails(): LiveData<MutableList<Cocktail>>
    suspend fun getCocktail(uuid: UUID) : LiveData<Cocktail>
}