package bb.incognito.repositories

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

import java.util.ArrayList

import bb.incognito.AppDatabase
import bb.incognito.dao.CocktailDao
import bb.incognito.model.Cocktail
import bb.incognito.model.Guest

class CocktailRepository(application: Application) {
    private val cocktailDao: CocktailDao
    val allCocktails: LiveData<MutableList<Cocktail>>

    init {
        val db = AppDatabase.getDatabase(application)
        cocktailDao = db.cocktailDao()
        allCocktails = cocktailDao.all
    }

    fun insert(cocktail: Cocktail) {
        insertAsyncTask(cocktailDao).execute(cocktail)
    }

    private class insertAsyncTask internal constructor(private val cocktailDao: CocktailDao) : AsyncTask<Cocktail, Void, Void>() {

        override fun doInBackground(vararg params: Cocktail): Void? {
            cocktailDao.insertCocktail(params[0])
            return null
        }
    }
}
