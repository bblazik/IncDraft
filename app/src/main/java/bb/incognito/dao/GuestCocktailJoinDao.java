package bb.incognito.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import bb.incognito.model.Cocktail;
import bb.incognito.model.Guest;
import bb.incognito.model.GuestCocktailJoin;

@Dao
public interface GuestCocktailJoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GuestCocktailJoin guestCocktailJoin);

    @Query("SELECT * FROM Guest " +
            "INNER JOIN guest_cocktail_join ON Guest.id = guest_cocktail_join.guestId " +
            "WHERE guest_cocktail_join.cocktailId = :cocktailId")
    List<Guest> getUsersForCocktail(final int cocktailId);

    @Query("SELECT * FROM cocktail" +
            " INNER JOIN guest_cocktail_join ON cocktail.cocktail_id=guest_cocktail_join.cocktailId " +
            "WHERE guest_cocktail_join.guestId=:guestId")
    LiveData<List<Cocktail>> getCocktailsForUsers(final int guestId);

    @Query("DELETE FROM guest_cocktail_join")
    void deleteAll();

    @Delete
    void delete(GuestCocktailJoin guestCocktailJoin);
}
