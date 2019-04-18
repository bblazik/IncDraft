package bb.incognito.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import java.util.UUID

import bb.incognito.model.Guest

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
