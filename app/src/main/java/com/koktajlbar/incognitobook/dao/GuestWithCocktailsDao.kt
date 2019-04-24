package com.koktajlbar.incognitobook.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query

import com.koktajlbar.incognitobook.model.GuestWithCocktails

@Dao
interface GuestWithCocktailsDao {
    @Query("SELECT * FROM Guest")
    fun loadGuestsAndCocktails(): LiveData<MutableList<GuestWithCocktails>>
}

