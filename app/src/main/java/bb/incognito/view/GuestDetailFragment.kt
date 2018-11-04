package bb.incognito.view

import android.arch.lifecycle.Observer
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
import bb.incognito.model.Cocktail
import bb.incognito.model.GuestCocktailJoin
import bb.incognito.model.GuestWithCocktails
import bb.incognito.repositories.CocktailRepository
import bb.incognito.repositories.GuestWithCocktailsRepository
import bb.incognito.utils.SwipeToDeleteCallback
import bb.incognito.view.adapter.CocktailAdapter
import bb.incognito.viewModel.GuestDetailVM

class GuestDetailFragment : Fragment(), SearchView.OnQueryTextListener{

    var guestDetailFragmentBinding: GuestDetailFragmentBinding? = null
    var guestDetailViewModel: GuestDetailVM? = null
    var cocktailAdapter: CocktailAdapter? = null
    var cocktailRepository: CocktailRepository? = null
    var guestWithCocktailsRepository: GuestWithCocktailsRepository? = null
    private var sv: SearchView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        guestDetailFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.guest_detail_fragment, container, false)
        initDataBinding()
        return guestDetailFragmentBinding!!.root
    }

    fun initDataBinding() {
        var guest =  activity.intent.extras.getParcelable("GUEST") as GuestWithCocktails
        guestWithCocktailsRepository = GuestWithCocktailsRepository(activity.application)
        cocktailRepository = CocktailRepository(activity.application)
        guest.cocktailList = guestWithCocktailsRepository?.getCocktails(guest.guest.id)

        setupAdapter(guest)
        guestDetailViewModel = GuestDetailVM(guest, activity.supportFragmentManager, cocktailRepository)
        guestDetailFragmentBinding!!.viewModel = guestDetailViewModel

        sv = activity.findViewById(R.id.search)
        sv!!.setOnQueryTextListener(this)
    }

    private fun setupAdapter(guest: GuestWithCocktails) {
        cocktailAdapter = CocktailAdapter() //get data of cocktails. from guest
        guest!!.cocktailList!!.observe(this,
                Observer<MutableList<Cocktail>> { cocktails -> cocktailAdapter!!.cocktailList = cocktails!! })

        val swipeHandler = object : SwipeToDeleteCallback(context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val removedCocktail = cocktailAdapter!!.cocktailList.get(viewHolder.adapterPosition)
                val removedPosition = viewHolder.adapterPosition

                cocktailAdapter!!.removeAt(viewHolder.adapterPosition)
                guestWithCocktailsRepository!!.removeRelation(GuestCocktailJoin(guest.guest.id, removedCocktail.id))


                val snackbar = Snackbar.make(guestDetailFragmentBinding!!.getRoot(), "Removed cocktail: " + removedCocktail.name, Snackbar.LENGTH_LONG)
                snackbar.setAction("UNDO") { cocktailAdapter!!.restoreItem(removedPosition, removedCocktail); guestWithCocktailsRepository!!.insertRelation(GuestCocktailJoin(guest.guest.id, removedCocktail.id)) }
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
