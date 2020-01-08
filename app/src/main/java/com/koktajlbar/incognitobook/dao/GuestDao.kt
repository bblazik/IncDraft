package com.koktajlbar.incognitobook.dao



import androidx.lifecycle.LiveData
import androidx.room.*
import com.koktajlbar.incognitobook.model.Guest

@Dao
interface GuestDao {
    @Query("SELECT * FROM Guest")
    fun allGuests(): LiveData<List<Guest>>

    @Query("SELECT * FROM Guest WHERE id = :id")
    fun getGuest(id: Int): LiveData<Guest>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGuest(guest: Guest)

    @Query("DELETE FROM guest")
    fun deleteAll()

    @Delete
    fun delete(guest: Guest)

    @Update
    fun update(guest: Guest)
}
