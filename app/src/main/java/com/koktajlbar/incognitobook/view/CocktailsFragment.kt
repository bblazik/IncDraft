package com.koktajlbar.incognitobook.view


import android.app.Application
import androidx.lifecycle.Observer
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.koktajlbar.incognitobook.R
import com.koktajlbar.incognitobook.databinding.FragmentCocktailsBinding
import com.koktajlbar.incognitobook.model.Cocktail
import com.koktajlbar.incognitobook.view.adapter.CocktailAdapter
import com.koktajlbar.incognitobook.viewModel.CocktailsViewModelFactory
import com.koktajlbar.incognitobook.viewModel.CocktailsViewModel

class CocktailsFragment : Fragment(), SearchView.OnQueryTextListener {

    private var viewModelFactory: CocktailsViewModelFactory? = null
    private var viewModel: CocktailsViewModel? = null
    private var adapter: CocktailAdapter? = null
    private var sv: SearchView? = null
    var signature = false
    internal var cocktailFragmentBinding: FragmentCocktailsBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewModelFactory = CocktailsViewModelFactory(context?.applicationContext as Application)
        viewModel = ViewModelProvider(this, viewModelFactory!!).get(CocktailsViewModel::class.java)
        cocktailFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cocktails, container, false)

        setBinding()
        viewModel!!.allCocktails.observe(this,
                Observer<MutableList<Cocktail>> { cocktails ->
                    if (signature) adapter!!.cocktailList = cocktails!!.filter { cocktail -> cocktail.signature.equals(true) }.toMutableList()
                    else adapter!!.cocktailList = cocktails!!})
        sv!!.setOnQueryTextListener(this)
        return cocktailFragmentBinding!!.root
    }

    fun setBinding() {
        cocktailFragmentBinding!!.viewModel = viewModel
        cocktailFragmentBinding!!.setLifecycleOwner(this)

        adapter = CocktailAdapter(activity)
        cocktailFragmentBinding!!.list.adapter = adapter
        cocktailFragmentBinding!!.list.layoutManager = LinearLayoutManager(context)
        sv = activity!!.findViewById(R.id.search)
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
