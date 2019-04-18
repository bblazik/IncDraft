package bb.incognito.dao


import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import java.util.UUID

import bb.incognito.model.Cocktail
import bb.incognito.model.Guest
import bb.incognito.model.GuestCocktailJoin

@Dao
interface GuestCocktailJoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(guestCocktailJoin: GuestCocktailJoin)

    @Query("SELECT * FROM Guest " +
            "INNER JOIN guest_cocktail_join ON Guest.id = guest_cocktail_join.guestId " +
            "WHERE guest_cocktail_join.cocktailId = :cocktailId")
    fun getUsersForCocktail(cocktailId: UUID): List<Guest>

    @Query("SELECT * FROM cocktail" +
            " INNER JOIN guest_cocktail_join ON cocktail.cocktail_id=guest_cocktail_join.cocktailId " +
            "WHERE guest_cocktail_join.guestId=:guestId")
    fun getCocktailsForUsers(guestId: UUID): LiveData<List<Cocktail>>

    @Query("DELETE FROM guest_cocktail_join")
    fun deleteAll()

    @Delete
    fun delete(guestCocktailJoin: GuestCocktailJoin)
}
