package com.koktajlbar.incognitobook;
import java.util.List;

import com.koktajlbar.incognitobook.model.Cocktail;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET("cocktails")
    Call<List<Cocktail>> getCocktails();
}
