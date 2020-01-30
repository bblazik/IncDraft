package com.koktajlbar.incognitobook.view

import androidx.lifecycle.Observer
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
import com.koktajlbar.incognitobook.databinding.GuestDetailFragmentBinding
import com.koktajlbar.incognitobook.model.Cocktail
import com.koktajlbar.incognitobook.model.GuestCocktailJoin
import com.koktajlbar.incognitobook.model.GuestWithCocktails
import com.koktajlbar.incognitobook.repositories.CocktailRepository
import com.koktajlbar.incognitobook.repositories.GuestWithCocktailsRepository
import com.koktajlbar.incognitobook.utils.SwipeToDeleteCallback
import com.koktajlbar.incognitobook.view.adapter.CocktailAdapter
import com.koktajlbar.incognitobook.viewModel.GuestDetailVM

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
        var guest =  activity!!.intent.extras.getParcelable("GUEST") as GuestWithCocktails
        guestWithCocktailsRepository = GuestWithCocktailsRepository(activity!!.application)
        cocktailRepository = CocktailRepository(activity!!.application)
        guest.cocktailList = guestWithCocktailsRepository?.getCocktails(guest.guest.id)

        setupAdapter(guest)
        guestDetailViewModel = GuestDetailVM(guest, activity!!.supportFragmentManager, cocktailRepository)
        guestDetailFragmentBinding!!.viewModel = guestDetailViewModel

        sv = activity!!.findViewById(R.id.search)
        sv!!.setOnQueryTextListener(this)
    }

    private fun setupAdapter(guest: GuestWithCocktails) {
        cocktailAdapter = CocktailAdapter(activity) //get data of cocktails. from guest
        guest!!.cocktailList!!.observe(this,
                Observer<MutableList<Cocktail>> { cocktails -> cocktailAdapter!!.cocktailList = cocktails!! })

        val swipeHandler = object : SwipeToDeleteCallback(context!!) {
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
