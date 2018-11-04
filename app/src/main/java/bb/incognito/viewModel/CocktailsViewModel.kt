package bb.incognito.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.view.View
import bb.incognito.R
import bb.incognito.model.Cocktail
import bb.incognito.repositories.CocktailRepository
import bb.incognito.repositories.GuestWithCocktailsRepository
import bb.incognito.view.AddCocktailFragment
import bb.incognito.view.AddCocktailFragment.Companion.guest

class CocktailsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CocktailRepository
    private val guestWithCocktailsRepository : GuestWithCocktailsRepository
    val allCocktails: LiveData<MutableList<Cocktail>>
    //val cocktailsForGuest : LiveData<List<Cocktail>>
    init {
        repository = CocktailRepository(application)
        guestWithCocktailsRepository = GuestWithCocktailsRepository(application)

        //cocktailsForGuest = guestWithCocktailsRepository.getCocktails()
        allCocktails = repository.allCocktails
    }

    fun onClick(view: View) {
        //fragmentManager.beginTransaction().replace(R.id.container2, AddCocktailFragment.launch(guest)).addToBackStack(null).commit()
    }
}