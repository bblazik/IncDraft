package bb.incognito.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView

import bb.incognito.R
import bb.incognito.view.adapter.GuestAdapter
import bb.incognito.viewModel.GuestsViewModel
import bb.incognito.databinding.GuestsFragmentBinding
import bb.incognito.model.Guest
import bb.incognito.model.GuestWithCocktails

class GuestsFragment : Fragment(), SearchView.OnQueryTextListener {

    private var guestsViewModel: GuestsViewModel? = null
    private var guestAdapter: GuestAdapter? = null
    private var sv: SearchView? = null
    internal var guestFragmentBinding: GuestsFragmentBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        guestsViewModel = ViewModelProviders.of(this).get(GuestsViewModel::class.java)
        guestsViewModel!!.setFragmentManager(this.fragmentManager)
        guestFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.guests_fragment, container, false)

        setBinding()
        guestsViewModel!!.allGuests.observe(this,
                Observer<List<GuestWithCocktails>> { guestsWithCocktails -> guestAdapter!!.setGuests(guestsWithCocktails) })
        sv!!.setOnQueryTextListener(this)
        return guestFragmentBinding!!.root
    }

    fun setBinding() {
        guestFragmentBinding!!.viewModel = guestsViewModel
        guestFragmentBinding!!.setLifecycleOwner(this)

        guestAdapter = GuestAdapter()
        guestAdapter!!.itemTouchHelper.attachToRecyclerView(guestFragmentBinding!!.list)
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
