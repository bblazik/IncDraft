package com.koktajlbar.incognitobook

import androidx.lifecycle.LiveData
import com.koktajlbar.incognitobook.model.Cocktail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

interface CocktailApi {
    @GET("cocktails/{uuid}")
    suspend fun getCocktail(@Path("uuid") uuid: UUID): Cocktail

    @GET("cocktails")
    suspend fun allCocktails(): MutableList<Cocktail>
}