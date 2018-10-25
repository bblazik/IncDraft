package bb.incognito.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.support.v4.app.FragmentManager
import bb.incognito.model.Cocktail
import bb.incognito.repositories.CocktailRepository

class CocktailsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CocktailRepository
    val allCocktails: LiveData<List<Cocktail>>
    init {
        repository = CocktailRepository(application)
        allCocktails = repository.allCocktails
    }
}