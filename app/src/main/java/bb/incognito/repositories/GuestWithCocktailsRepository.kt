package bb.incognito.repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import java.util.UUID

import bb.incognito.AppDatabase
import bb.incognito.dao.GuestCocktailJoinDao
import bb.incognito.dao.GuestDao
import bb.incognito.dao.GuestWithCocktailsDao
import bb.incognito.model.Cocktail
import bb.incognito.model.Guest
import bb.incognito.model.GuestCocktailJoin
import bb.incognito.model.GuestWithCocktails

class GuestWithCocktailsRepository(application: Application) {
    private val guestWithCocktailsDao: GuestWithCocktailsDao
    private val guestCocktailJoinDao: GuestCocktailJoinDao
    val allGuests: LiveData<MutableList<GuestWithCocktails>>
    private val cocktailsForGuest: LiveData<List<Cocktail>>? = null

    init {
        val db = AppDatabase.getDatabase(application)
        guestWithCocktailsDao = db.guestWithCocktailsDao()
        guestCocktailJoinDao = db.guestCocktailJoinDao()
        allGuests = guestWithCocktailsDao.loadGuestsAndCocktails()
    }

    fun getCocktails(guestId: UUID): LiveData<MutableList<Cocktail>> {
        return guestCocktailJoinDao.getCocktailsForUsers(guestId)
    }

    fun insertRelation(guestCocktailJoin: GuestCocktailJoin) {
        GuestWithCocktailsRepository.insertAsyncTask(guestCocktailJoinDao).execute(guestCocktailJoin)
    }

    fun removeRelation(guestCocktailJoin: GuestCocktailJoin) {
        GuestWithCocktailsRepository.deleteAsyncTask(guestCocktailJoinDao).execute(guestCocktailJoin)
    }

    private class insertAsyncTask internal constructor(private val guestCocktailJoinDao: GuestCocktailJoinDao) : AsyncTask<GuestCocktailJoin, Void, Void>() {

        override fun doInBackground(vararg params: GuestCocktailJoin): Void? {
            guestCocktailJoinDao.insert(params[0])
            return null
        }
    }

    private class deleteAsyncTask internal constructor(private val guestCocktailJoinDao: GuestCocktailJoinDao) : AsyncTask<GuestCocktailJoin, Void, Void>() {

        override fun doInBackground(vararg params: GuestCocktailJoin): Void? {
            guestCocktailJoinDao.delete(params[0])
            return null
        }
    }
}