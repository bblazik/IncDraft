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
import bb.incognito.databinding.FragmentCocktailsBinding
import bb.incognito.model.Cocktail
import bb.incognito.model.GuestWithCocktails
import bb.incognito.view.adapter.CocktailAdapter
import bb.incognito.viewModel.CocktailsViewModel
import bb.incognito.viewModel.GuestsViewModel

class CocktailsFragment : Fragment(), SearchView.OnQueryTextListener {

    private var viewModel: CocktailsViewModel? = null
    private var adapter: CocktailAdapter? = null
    private var sv: SearchView? = null
    internal var cocktailFragmentBinding: FragmentCocktailsBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(CocktailsViewModel::class.java)
        cocktailFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cocktails, container, false)

        setBinding()
        viewModel!!.allCocktails.observe(this,
                Observer<List<Cocktail>> { cocktails -> adapter!!.setCocktailList(cocktails) })
        sv!!.setOnQueryTextListener(this)
        return cocktailFragmentBinding!!.root
    }

    fun setBinding() {
        cocktailFragmentBinding!!.viewModel = viewModel
        cocktailFragmentBinding!!.setLifecycleOwner(this)

        adapter = CocktailAdapter()
        cocktailFragmentBinding!!.list.adapter = adapter
        cocktailFragmentBinding!!.list.layoutManager = LinearLayoutManager(context)
        sv = activity.findViewById(R.id.search)
    }

    override fun onQueryTextSubmit(s: String): Boolean {
        adapter!!.filter.filter(s)
        return false
    }

    override fun onQueryTextChange(s: String): Boolean {
        adapter!!.filter.filter(s)
        return false
    }

}
