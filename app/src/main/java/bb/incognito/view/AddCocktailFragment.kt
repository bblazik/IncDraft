package bb.incognito.view

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
import bb.incognito.R
import bb.incognito.databinding.FragmentAddCocktailBinding
import bb.incognito.model.Cocktail
import bb.incognito.model.Guest
import bb.incognito.model.GuestWithCocktails
import bb.incognito.repositories.CocktailRepository
import bb.incognito.repositories.GuestRepository
import bb.incognito.repositories.GuestWithCocktailsRepository
import bb.incognito.view.adapter.CocktailAdapter
import bb.incognito.viewModel.CocktailsViewModel

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
        cocktailAdapter?.setCheckable(true)
        fragmentAddCocktailBinding!!.list.adapter = cocktailAdapter
        fragmentAddCocktailBinding!!.list.layoutManager = LinearLayoutManager(context)

        viewModel.allCocktails.observe(this,
                Observer<List<Cocktail>> { cocktails -> cocktailAdapter!!.cocktailList = cocktails })
        sv = activity.findViewById(R.id.search)

        fragmentAddCocktailBinding?.cancelButton?.setOnClickListener {
            fragmentManager.popBackStack()
        }

        fragmentAddCocktailBinding?.acceptButton?.setOnClickListener {
            var guestWithCocktailRepository = GuestWithCocktailsRepository(activity.application)
            var guestRepository = GuestRepository(activity.application)
            var checkedCocktails = cocktailAdapter!!.checkedCocktails

            //guestRepository.insert(guest)
            //guestWithCocktailRepository.insert(guest)
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
