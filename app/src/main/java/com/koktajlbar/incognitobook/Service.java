package com.koktajlbar.incognitobook;
import com.koktajlbar.incognitobook.model.Cocktail;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Service {
    @GET("cocktails/{uuid}")
    Call<Cocktail> getCocktail(@Path("uuid") String uuid);

    @GET("cocktails")
    Call<List<Cocktail>> getCocktails();
}
