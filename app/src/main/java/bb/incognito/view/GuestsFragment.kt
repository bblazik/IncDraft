package bb.incognito.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView

import bb.incognito.R
import bb.incognito.view.adapter.GuestAdapter
import bb.incognito.viewModel.GuestsViewModel
import bb.incognito.databinding.GuestsFragmentBinding
import bb.incognito.model.Guest
import bb.incognito.model.GuestCocktailJoin
import bb.incognito.model.GuestWithCocktails
import bb.incognito.repositories.CocktailRepository
import bb.incognito.repositories.GuestRepository
import bb.incognito.repositories.GuestWithCocktailsRepository
import bb.incognito.utils.SwipeToDeleteCallback

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
        guestsViewModel!!.setFragmentManager(this.fragmentManager)
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
        guestRepository = GuestRepository(activity.application)
        guestWithCocktailsRepository = GuestWithCocktailsRepository(activity.application)
        var cocktailRepository = CocktailRepository(activity.application)

        val swipeHandler = object : SwipeToDeleteCallback(context) {
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
        sv = activity.findViewById(R.id.search)
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
