package bb.incognito.view

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
import bb.incognito.databinding.GuestDetailFragmentBinding
import bb.incognito.model.Guest
import bb.incognito.model.GuestWithCocktails
import bb.incognito.repositories.CocktailRepository
import bb.incognito.utils.SwipeToDeleteCallback
import bb.incognito.view.adapter.CocktailAdapter
import bb.incognito.viewModel.GuestDetailVM

class GuestDetailFragment : Fragment(), SearchView.OnQueryTextListener{

    var guestDetailFragmentBinding: GuestDetailFragmentBinding? = null
    var guestDetailViewModel: GuestDetailVM? = null
    var cocktailAdapter: CocktailAdapter? = null
    var cocktailRepository: CocktailRepository? = null
    private var sv: SearchView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        guestDetailFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.guest_detail_fragment, container, false)
        initDataBinding()
        return guestDetailFragmentBinding!!.root
    }

    fun initDataBinding() {
        var guest =  activity.intent.extras.getParcelable("GUEST") as GuestWithCocktails
        cocktailRepository = CocktailRepository(activity.application)

        setupAdapter(guest)
        guestDetailViewModel = GuestDetailVM(guest, activity.supportFragmentManager, cocktailRepository)
        guestDetailFragmentBinding!!.viewModel = guestDetailViewModel

        sv = activity.findViewById(R.id.search)
        sv!!.setOnQueryTextListener(this)
    }

    private fun setupAdapter(guest: GuestWithCocktails) {
        cocktailAdapter = CocktailAdapter() //get data of cocktails. from guest
        cocktailAdapter!!.setCocktailList(guest.cocktailList)
        val swipeHandler = object : SwipeToDeleteCallback(context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val removedCocktail = cocktailAdapter!!.getCocktailList().get(viewHolder.adapterPosition)
                val removedPosition = viewHolder.adapterPosition

                cocktailAdapter!!.removeAt(viewHolder.adapterPosition)

                val snackbar = Snackbar.make(guestDetailFragmentBinding!!.getRoot(), "Removed cocktail: " + removedCocktail.name, Snackbar.LENGTH_LONG)
                snackbar.setAction("UNDO") { cocktailAdapter!!.restoreItem(removedPosition, removedCocktail) }
                snackbar.setActionTextColor(Color.YELLOW)
                snackbar.show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(guestDetailFragmentBinding!!.list)
        guestDetailFragmentBinding!!.list.adapter = cocktailAdapter
        guestDetailFragmentBinding!!.list.layoutManager = LinearLayoutManager(context)
    }

    override fun onQueryTextSubmit(s: String): Boolean {
        cocktailAdapter!!.filter.filter(s)
        return false
    }

    override fun onQueryTextChange(s: String): Boolean {
        cocktailAdapter!!.filter.filter(s)
        return false
    }
}
