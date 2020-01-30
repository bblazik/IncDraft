package com.koktajlbar.incognitobook.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import android.os.AsyncTask

import com.koktajlbar.incognitobook.AppDatabase
import com.koktajlbar.incognitobook.dao.CocktailDao
import com.koktajlbar.incognitobook.model.Cocktail

class CocktailRepository(application: Application) {
    private val cocktailDao: CocktailDao
    val allCocktails: LiveData<MutableList<Cocktail>>

    init {
        val db = AppDatabase.getDatabase(application)
        cocktailDao = db.cocktailDao()
        allCocktails = cocktailDao.all
    }

    fun getCocktail(uuid: UUID): LiveData<Cocktail> {
        return cocktailDao.find_by_uuid(uuid)
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
