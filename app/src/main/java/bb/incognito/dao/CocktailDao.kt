package bb.incognito.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import java.util.UUID

import bb.incognito.model.Cocktail
import bb.incognito.model.Guest

@Dao
interface CocktailDao {

    @get:Query("SELECT * FROM Cocktail GROUP BY name")
    val all: LiveData<List<Cocktail>>

    @Query("SELECT * FROM Cocktail WHERE cocktail_id IN (:cocktailIds)")
    fun getCocktailsForGuest(cocktailIds: List<Int>): LiveData<List<Cocktail>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCocktail(cocktail: Cocktail)

    @Query("DELETE FROM cocktail")
    fun deleteAll()
}
