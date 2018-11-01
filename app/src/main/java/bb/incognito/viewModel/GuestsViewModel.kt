package bb.incognito.viewModel

import android.app.Application
import android.support.v4.app.FragmentManager
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.view.View

import bb.incognito.repositories.GuestRepository
import bb.incognito.view.AddGuestFragment

class GuestsViewModel(application: Application) : AndroidViewModel(application) {
    private val guestRepository: GuestRepository
    private val guestWithCocktailsRepository: GuestWithCocktailsRepository
    val allGuests: LiveData<List<GuestWithCocktails>>
    private var fragmentManager: FragmentManager? = null

    fun setFragmentManager(fragmentManager: FragmentManager) {
        this.fragmentManager = fragmentManager
    }

    init {
        guestRepository = GuestRepository(application)
        guestWithCocktailsRepository = GuestWithCocktailsRepository(application)
        allGuests = guestWithCocktailsRepository.allGuests
    }

    fun onClick(view: View) {
        val newFragment = AddGuestFragment.newInstance(false, -1, guestRepository)
        newFragment.show(fragmentManager!!, "New dialog")
    }
}
