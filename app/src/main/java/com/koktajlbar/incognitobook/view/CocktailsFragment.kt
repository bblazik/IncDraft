package com.koktajlbar.incognitobook.view

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.koktajlbar.incognitobook.R
import com.koktajlbar.incognitobook.databinding.FragmentCocktailsBinding
import com.koktajlbar.incognitobook.model.Cocktail
import com.koktajlbar.incognitobook.view.adapter.CocktailAdapter
import com.koktajlbar.incognitobook.viewmodels.CocktailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class CocktailsFragment : Fragment() {
    private val cocktailsViewModel: CocktailsViewModel by viewModel()

    private val onQueryTextListener: SearchView.OnQueryTextListener = object : SearchView.OnQueryTextListener {
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
    private var cocktailFragmentBinding: FragmentCocktailsBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        cocktailFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cocktails, container, false)

        setBinding()

        cocktailsViewModel.cocktails.observe(viewLifecycleOwner,
                Observer<MutableList<Cocktail>> { cocktails ->
                    if (signature) adapter!!.cocktailList = cocktails.filter { cocktail -> cocktail.signature }.toMutableList()
                    else adapter!!.cocktailList = cocktails})

        sv!!.setOnQueryTextListener(onQueryTextListener)
        return cocktailFragmentBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = requireActivity().findViewById(R.id.list)
        recyclerView.layoutManager = GridLayoutManager(context, resources.getInteger(R.integer.recycler_view_column_count))
    }

    private fun setBinding() {
        cocktailFragmentBinding!!.viewModel = cocktailsViewModel
        cocktailFragmentBinding!!.lifecycleOwner = this

        adapter = CocktailAdapter(activity)
        cocktailFragmentBinding!!.list.adapter = adapter
        cocktailFragmentBinding!!.list.layoutManager = LinearLayoutManager(context)
        sv = requireActivity().findViewById(R.id.search)
    }
}