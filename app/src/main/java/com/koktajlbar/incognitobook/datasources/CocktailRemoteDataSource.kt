package com.koktajlbar.incognitobook.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.koktajlbar.incognitobook.CocktailApi
import com.koktajlbar.incognitobook.model.Cocktail
import java.util.*
import javax.inject.Inject

class CocktailRemoteDataSource(private val cocktailApi: CocktailApi) : CocktailDataSource {
    override suspend fun allCocktails(): LiveData<MutableList<Cocktail>> {
        return MutableLiveData(cocktailApi.allCocktails())
    }

    override suspend fun getCocktail(uuid: UUID): LiveData<Cocktail> {
        return MutableLiveData(cocktailApi.getCocktail(uuid))
    }

}