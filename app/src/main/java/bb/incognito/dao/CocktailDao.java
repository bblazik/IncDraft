package bb.incognito.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import bb.incognito.model.Cocktail;
import bb.incognito.model.Guest;

@Dao
public interface CocktailDao {

    @Query("SELECT * FROM Cocktail GROUP BY name")
    LiveData<List<Cocktail>> getAll();

    @Query("SELECT * FROM Cocktail WHERE id IN (:cocktailIds)")
    LiveData<List<Cocktail>> getCocktailsForGuest(List<Integer> cocktailIds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCocktail(Cocktail cocktail);

    @Query("DELETE FROM cocktail")
    void deleteAll();
}
