package com.koktajlbar.incognitobook.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.koktajlbar.incognitobook.R
import com.koktajlbar.incognitobook.databinding.FragmentCocktailsBinding
import com.koktajlbar.incognitobook.model.Cocktail
import com.koktajlbar.incognitobook.view.adapter.CocktailAdapter
import com.koktajlbar.incognitobook.viewModel.CocktailsViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class CocktailsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<CocktailsViewModel> { viewModelFactory }

    private val onQueryTextListener: OnQueryTextListener = object : OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            adapter!!.filter.filter(query)
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            adapter!!.filter.filter(newText)
            return false
        }
    }

    private var adapter: CocktailAdapter? = null
    private var sv: SearchView? = null
    var signature = false
    internal var cocktailFragmentBinding: FragmentCocktailsBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        cocktailFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cocktails, container, false)

        setBinding()
        viewModel!!.cocktails.observe(viewLifecycleOwner,
                Observer<MutableList<Cocktail>> { cocktails ->
                    if (signature) adapter!!.cocktailList = cocktails.filter { cocktail -> cocktail.signature.equals(true) }.toMutableList()
                    else adapter!!.cocktailList = cocktails})

        sv!!.setOnQueryTextListener(onQueryTextListener)
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
}
