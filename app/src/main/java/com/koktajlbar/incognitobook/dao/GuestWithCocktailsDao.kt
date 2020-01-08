package com.koktajlbar.incognitobook.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

import com.koktajlbar.incognitobook.model.GuestWithCocktails

@Dao
interface GuestWithCocktailsDao {
    @Query("SELECT * FROM Guest")
    fun loadGuestsAndCocktails(): LiveData<MutableList<GuestWithCocktails>>
}

