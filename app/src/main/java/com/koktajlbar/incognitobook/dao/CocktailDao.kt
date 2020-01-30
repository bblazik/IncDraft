package com.koktajlbar.incognitobook.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.koktajlbar.incognitobook.model.Cocktail
import java.util.*

@Dao
interface CocktailDao {
    @Query("SELECT * FROM Cocktail WHERE cocktail_id = :id")
    fun find_by_uuid(id: UUID): LiveData<Cocktail>

    @get:Query("SELECT * FROM Cocktail GROUP BY name")
    val all: LiveData<MutableList<Cocktail>>

    @Query("SELECT * FROM Cocktail WHERE cocktail_id IN (:cocktailIds)")
    fun getCocktailsForGuest(cocktailIds: List<Int>): LiveData<List<Cocktail>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCocktail(cocktail: Cocktail)

    @Query("DELETE FROM cocktail")
    fun deleteAll()
}
