package com.koktajlbar.incognitobook.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import com.koktajlbar.incognitobook.model.Cocktail

@Dao
interface CocktailDao {

    @get:Query("SELECT * FROM Cocktail GROUP BY name")
    val all: LiveData<MutableList<Cocktail>>

    @Query("SELECT * FROM Cocktail WHERE cocktail_id IN (:cocktailIds)")
    fun getCocktailsForGuest(cocktailIds: List<Int>): LiveData<List<Cocktail>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCocktail(cocktail: Cocktail)

    @Query("DELETE FROM cocktail")
    fun deleteAll()
}
