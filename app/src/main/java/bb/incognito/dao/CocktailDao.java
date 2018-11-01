package bb.incognito.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import bb.incognito.model.Cocktail;

@Dao
public interface CocktailDao {

    @Query("SELECT * FROM Cocktail GROUP BY name")
    LiveData<List<Cocktail>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCocktail(Cocktail cocktail);
}
