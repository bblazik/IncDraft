package com.koktajlbar.incognitobook.view

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView

import com.koktajlbar.incognitobook.R
import com.koktajlbar.incognitobook.view.adapter.GuestAdapter
import com.koktajlbar.incognitobook.viewModel.GuestsViewModel
import com.koktajlbar.incognitobook.databinding.GuestsFragmentBinding
import com.koktajlbar.incognitobook.model.GuestWithCocktails
import com.koktajlbar.incognitobook.repositories.CocktailRepository
import com.koktajlbar.incognitobook.repositories.GuestRepository
import com.koktajlbar.incognitobook.repositories.GuestWithCocktailsRepository
import com.koktajlbar.incognitobook.utils.SwipeToDeleteCallback

class GuestsFragment : Fragment(), SearchView.OnQueryTextListener {

    private var guestsViewModel: GuestsViewModel? = null
    private var guestAdapter: GuestAdapter? = null
    private var sv: SearchView? = null
    internal var guestFragmentBinding: GuestsFragmentBinding? = null
    var guestRepository: GuestRepository? = null
    var guestWithCocktailsRepository: GuestWithCocktailsRepository? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        guestsViewModel = ViewModelProviders.of(this).get(GuestsViewModel::class.java)
        guestsViewModel!!.setFragmentManager(this.fragmentManager!!)
        guestFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.guests_fragment, container, false)

        setBinding()
        guestsViewModel!!.allGuests.observe(this,
                Observer<MutableList<GuestWithCocktails>> { guestsWithCocktails -> guestAdapter!!.setGuests(guestsWithCocktails) })
        sv!!.setOnQueryTextListener(this)
        return guestFragmentBinding!!.root
    }

    fun setBinding() {
        guestFragmentBinding!!.viewModel = guestsViewModel
        guestFragmentBinding!!.setLifecycleOwner(this)

        guestAdapter = GuestAdapter()
        guestRepository = GuestRepository(activity!!.application)
        guestWithCocktailsRepository = GuestWithCocktailsRepository(activity!!.application)
        var cocktailRepository = CocktailRepository(activity!!.application)

        val swipeHandler = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val removedGuest = guestAdapter!!.getGuests().get(viewHolder.adapterPosition)
                val removedPosition = viewHolder.adapterPosition
                guestAdapter!!.removeAt(viewHolder.adapterPosition)
                guestRepository!!.delete(removedGuest.guest)

                val snackbar = Snackbar.make(guestFragmentBinding!!.getRoot(), "Removed cocktail: " + removedGuest.name, Snackbar.LENGTH_LONG)
                snackbar.setAction("UNDO") { guestAdapter!!.restoreItem(removedPosition, removedGuest); }
                snackbar.setActionTextColor(Color.YELLOW)
                snackbar.show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(guestFragmentBinding!!.list)
        guestFragmentBinding!!.list.adapter = guestAdapter
        guestFragmentBinding!!.list.layoutManager = LinearLayoutManager(context)
        sv = activity!!.findViewById(R.id.search)
    }

    override fun onQueryTextSubmit(s: String): Boolean {
        guestAdapter!!.filter.filter(s)
        return false
    }

    override fun onQueryTextChange(s: String): Boolean {
        guestAdapter!!.filter.filter(s)
        return false
    }
}
