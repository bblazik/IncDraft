package bb.incognito.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import bb.incognito.model.GuestWithCocktails;

@Dao
public interface GuestWithCocktailsDao {
    @Query("SELECT * FROM Guest")
    public LiveData<List<GuestWithCocktails>> loadGuestsAndCocktails();
}
