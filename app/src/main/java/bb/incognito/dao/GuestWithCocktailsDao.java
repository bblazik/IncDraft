package bb.incognito.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import bb.incognito.model.Guest;
import bb.incognito.model.GuestWithCocktails;

@Dao
public interface GuestWithCocktailsDao {
    @Query("SELECT * FROM Guest")
    LiveData<List<GuestWithCocktails>> loadGuestsAndCocktails();
}

