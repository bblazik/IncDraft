package bb.incognito.repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

import bb.incognito.AppDatabase
import bb.incognito.dao.GuestDao
import bb.incognito.model.Guest
import bb.incognito.model.GuestWithCocktails

class GuestRepository(application: Application) {
    private val guestDao: GuestDao
    val allGuests: LiveData<List<Guest>>

    init {
        val db = AppDatabase.getDatabase(application)
        guestDao = db.guestDao()
        allGuests = guestDao.allGuests()
    }

    fun getGuest(id: Int?): LiveData<Guest> {
        return guestDao.getGuest(id!!)
    }

    fun update(guest: Guest) {
        updateAsyncTask(guestDao).execute(guest)
    }

    fun insert(guest: Guest) {
        insertAsyncTask(guestDao).execute(guest)
    }

    fun delete(guest: Guest) {
        deleteAsyncTask(guestDao).execute(guest)
    }

    private class insertAsyncTask internal constructor(private val guestDao: GuestDao) : AsyncTask<Guest, Void, Void>() {

        override fun doInBackground(vararg params: Guest): Void? {
            guestDao.insertGuest(params[0])
            return null
        }
    }

    private class updateAsyncTask internal constructor(private val guestDao: GuestDao) : AsyncTask<Guest, Void, Void>() {

        override fun doInBackground(vararg params: Guest): Void? {
            guestDao.update(params[0])
            return null
        }
    }

    private class deleteAsyncTask internal constructor(private val guestDao: GuestDao) : AsyncTask<Guest, Void, Void>() {

        override fun doInBackground(vararg params: Guest): Void? {
            guestDao.delete(params[0])
            return null
        }
    }
}