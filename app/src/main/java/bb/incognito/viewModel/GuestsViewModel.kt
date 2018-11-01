package bb.incognito.viewModel

import android.app.Application
import android.support.v4.app.FragmentManager
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.view.View
import bb.incognito.model.Cocktail
import bb.incognito.model.Guest
import bb.incognito.repositories.CocktailRepository

import bb.incognito.repositories.GuestRepository
import bb.incognito.view.AddGuestFragment

class GuestsViewModel(application: Application) : AndroidViewModel(application) {
    private val guestRepository: GuestRepository
    private val cocktailRepository: CocktailRepository
    val allGuests: LiveData<List<Guest>>
    private var fragmentManager: FragmentManager? = null

    fun setFragmentManager(fragmentManager: FragmentManager) {
        this.fragmentManager = fragmentManager
    }

    init {
        guestRepository = GuestRepository(application)
        cocktailRepository = CocktailRepository(application)
        allGuests = guestRepository.allGuests

        if (allGuests.value != null){
            for( guest in allGuests.value!!)
            {
                guest.cocktailList = cocktailRepository.getCocktailsForGuest(guest).value
            }
        }
    }

    fun onClick(view: View) {
        val newFragment = AddGuestFragment.newInstance(false, -1, guestRepository)
        newFragment.show(fragmentManager!!, "New dialog")
    }
}
