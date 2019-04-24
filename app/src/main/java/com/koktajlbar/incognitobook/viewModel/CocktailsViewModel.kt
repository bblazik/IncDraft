package com.koktajlbar.incognitobook.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.view.View
import com.koktajlbar.incognitobook.model.Cocktail
import com.koktajlbar.incognitobook.repositories.CocktailRepository
import com.koktajlbar.incognitobook.repositories.GuestWithCocktailsRepository

class CocktailsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CocktailRepository = CocktailRepository(application)
    private val guestWithCocktailsRepository : GuestWithCocktailsRepository
    val allCocktails: LiveData<MutableList<Cocktail>>
    init {
        guestWithCocktailsRepository = GuestWithCocktailsRepository(application)
        allCocktails = repository.allCocktails
    }

    fun onClick(view: View) {
        //fragmentManager.beginTransaction().replace(R.id.container2, AddCocktailFragment.launch(guest)).addToBackStack(null).commit()
    }
}