package bb.incognito;
import java.util.List;

import bb.incognito.model.Cocktail;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET("/cocktails/")
    Call<List<Cocktail>> getCocktails();
}
