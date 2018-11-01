package bb.incognito.viewModel

import android.app.Application
import android.support.v4.app.FragmentManager
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.view.View
import bb.incognito.model.GuestWithCocktails
import bb.incognito.repositories.CocktailRepository
import bb.incognito.repositories.GuestRepository

import bb.incognito.repositories.GuestWithCocktailsRepository
import bb.incognito.view.AddGuestFragment

class GuestsViewModel(application: Application) : AndroidViewModel(application) {
    private val guestWithCocktailsRepository: GuestWithCocktailsRepository
    private val guestRepository : GuestRepository
    private val cocktailRepository: CocktailRepository
    val allGuests: LiveData<List<GuestWithCocktails>>
    private var fragmentManager: FragmentManager? = null

    fun setFragmentManager(fragmentManager: FragmentManager) {
        this.fragmentManager = fragmentManager
    }

    init {
        guestWithCocktailsRepository = GuestWithCocktailsRepository(application)
        cocktailRepository = CocktailRepository(application)
        guestRepository = GuestRepository(application)
        allGuests = guestWithCocktailsRepository.allGuests
    }

    fun onClick(view: View) {
        val newFragment = AddGuestFragment.newInstance(false, -1, guestRepository)
        newFragment.show(fragmentManager!!, "New dialog")
    }
}
