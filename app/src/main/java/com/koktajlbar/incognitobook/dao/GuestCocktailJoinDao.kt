package com.koktajlbar.incognitobook.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.koktajlbar.incognitobook.model.Cocktail
import com.koktajlbar.incognitobook.model.Guest
import com.koktajlbar.incognitobook.model.GuestCocktailJoin
import java.util.*

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
    fun getCocktailsForUsers(guestId: UUID): LiveData<MutableList<Cocktail>>

    @Query("DELETE FROM guest_cocktail_join")
    fun deleteAll()

    @Delete
    fun delete(guestCocktailJoin: GuestCocktailJoin)
}
