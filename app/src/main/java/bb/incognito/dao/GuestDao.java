package bb.incognito.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import bb.incognito.model.Guest;

@Dao
public interface GuestDao {
    @Query("SELECT * FROM Guest")
    LiveData<List<Guest>> allGuests();

    @Query("SELECT * FROM Guest WHERE id = :id")
    LiveData<Guest> getGuest(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertGuest(Guest guest);

    @Query("DELETE FROM guest")
    void deleteAll();

    @Delete
    void delete(Guest guest);

    @Update
    void update(Guest guest);
}
