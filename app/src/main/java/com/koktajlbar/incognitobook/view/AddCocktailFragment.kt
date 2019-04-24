package com.koktajlbar.incognitobook.view

import android.arch.lifecycle.Observer
import android.support.v4.app.Fragment
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import com.koktajlbar.incognitobook.R
import com.koktajlbar.incognitobook.databinding.FragmentAddCocktailBinding
import com.koktajlbar.incognitobook.model.Cocktail
import com.koktajlbar.incognitobook.model.GuestCocktailJoin
import com.koktajlbar.incognitobook.model.GuestWithCocktails
import com.koktajlbar.incognitobook.repositories.CocktailRepository
import com.koktajlbar.incognitobook.repositories.GuestWithCocktailsRepository
import com.koktajlbar.incognitobook.view.adapter.CocktailAdapter
import com.koktajlbar.incognitobook.viewModel.CocktailsViewModel

class AddCocktailFragment : Fragment(), SearchView.OnQueryTextListener {

    internal var fragmentAddCocktailBinding: FragmentAddCocktailBinding? = null
    var cocktailAdapter: CocktailAdapter? = null
    var cocktailRepository : CocktailRepository? = null
    private var sv: SearchView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentAddCocktailBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_add_cocktail, container, false)
        cocktailRepository = CocktailRepository(activity.application)

        var viewModel = ViewModelProviders.of(this).get(CocktailsViewModel::class.java)
        cocktailAdapter = CocktailAdapter() //get data of cocktails. from guest
        cocktailAdapter?.checkable = true
        fragmentAddCocktailBinding!!.list.adapter = cocktailAdapter
        fragmentAddCocktailBinding!!.list.layoutManager = LinearLayoutManager(context)

        viewModel.allCocktails.observe(this,
                Observer<MutableList<Cocktail>> { cocktails -> cocktailAdapter!!.cocktailList = cocktails!! })
        sv = activity.findViewById(R.id.search)

        fragmentAddCocktailBinding?.cancelButton?.setOnClickListener {
            fragmentManager.popBackStack()
        }

        fragmentAddCocktailBinding?.acceptButton?.setOnClickListener {
            var guestWithCocktailRepository = GuestWithCocktailsRepository(activity.application)
            var checkedCocktails = cocktailAdapter!!.checkedCocktails

            for(cocktail in checkedCocktails)
                guestWithCocktailRepository.insertRelation(GuestCocktailJoin(guest!!.guest.id, cocktail.id))
            fragmentManager.popBackStack()
        }

        sv!!.setOnQueryTextListener(this)

        return fragmentAddCocktailBinding!!.root
    }

    override fun onQueryTextSubmit(s: String): Boolean {
        cocktailAdapter!!.filter.filter(s)
        return false
    }

    override fun onQueryTextChange(s: String): Boolean {
        cocktailAdapter!!.filter.filter(s)
        return false
    }

    companion object {
        var guest : GuestWithCocktails? = null
        fun launch(g: GuestWithCocktails) : Fragment{
            guest = g
            return AddCocktailFragment()
        }
    }
}
