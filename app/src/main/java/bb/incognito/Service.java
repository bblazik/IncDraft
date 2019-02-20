package bb.incognito;

import android.database.Observable;

import java.util.List;

import bb.incognito.model.Cocktail;
import retrofit2.Response;
import retrofit2.http.GET;

public interface Service {

//                          EXAMPLE
//    String ENDPOINT = "https://deckofcardsapi.com/";
//
//    /*
//     * Return new shuffled deck
//     * */
    @GET("cocktails")
    Response getCocktails();
}
