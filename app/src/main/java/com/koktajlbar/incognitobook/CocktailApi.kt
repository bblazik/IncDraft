package com.koktajlbar.incognitobook

import com.koktajlbar.incognitobook.model.Cocktail
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

interface CocktailApi {
    @GET("cocktails/{uuid}")
    suspend fun getCocktail(@Path("uuid") uuid: UUID): Cocktail

    @GET("cocktails")
    suspend fun allCocktails(): MutableList<Cocktail>
}