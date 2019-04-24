package com.koktajlbar.incognitobook.viewModel

import android.app.Application
import android.support.v4.app.FragmentManager
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.view.View
import com.koktajlbar.incognitobook.model.GuestWithCocktails
import com.koktajlbar.incognitobook.repositories.GuestRepository

import com.koktajlbar.incognitobook.repositories.GuestWithCocktailsRepository
import com.koktajlbar.incognitobook.view.AddGuestFragment

class GuestsViewModel(application: Application) : AndroidViewModel(application) {
    private val guestWithCocktailsRepository: GuestWithCocktailsRepository = GuestWithCocktailsRepository(application)
    private val guestRepository : GuestRepository = GuestRepository(application)

    val allGuests: LiveData<MutableList<GuestWithCocktails>>
    private var fragmentManager: FragmentManager? = null

    fun setFragmentManager(fragmentManager: FragmentManager) {
        this.fragmentManager = fragmentManager
    }

    init {
        allGuests = guestWithCocktailsRepository.allGuests
    }

    fun onClick(view: View) {
        val newFragment = AddGuestFragment.newInstance(false, -1, guestRepository)
        newFragment.show(fragmentManager!!, "New dialog")
    }
}
