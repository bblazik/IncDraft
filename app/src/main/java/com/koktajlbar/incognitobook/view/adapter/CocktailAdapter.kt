package com.koktajlbar.incognitobook.view.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.koktajlbar.incognitobook.R
import com.koktajlbar.incognitobook.databinding.CocktailRowBinding
import com.koktajlbar.incognitobook.model.Cocktail
import com.koktajlbar.incognitobook.viewmodels.CocktailRowVM
import java.util.*

class CocktailAdapter(val activity: FragmentActivity?) : RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder>(), Filterable {
    private var cocktails: MutableList<Cocktail> = ArrayList()
    private var filteredCocktails: MutableList<Cocktail> = ArrayList()
    private val filter = ItemFilter()
    private var checkable = false
    private var checkedCocktails: MutableList<Cocktail> = ArrayList()

    var cocktailList: MutableList<Cocktail>
        get() = cocktails
        set(cocktailList) {
            cocktails = cocktailList
            filteredCocktails = cocktailList
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val cardRowBinding = DataBindingUtil.inflate<CocktailRowBinding>(LayoutInflater.from(parent.context), R.layout.cocktail_row,
                parent, false)
        if (checkable) {
            cardRowBinding.checkBox.visibility = View.VISIBLE
            cardRowBinding.cocktailRow.setOnClickListener {
                val invertState = !cardRowBinding.checkBox.isChecked
                cardRowBinding.checkBox.isChecked = invertState
            }

        }
        return CocktailViewHolder(cardRowBinding)
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        holder.bindCard(filteredCocktails[position])
        holder.cocktailRowBinding.checkBox.setOnCheckedChangeListener { _, b ->
            if (b)
                checkedCocktails.add(cocktails[position])
            else
                checkedCocktails.remove(cocktails[position])
        }
    }

    override fun getItemCount(): Int {
        return filteredCocktails.size
    }

    override fun getFilter(): Filter {
        return filter
    }

    inner class CocktailViewHolder(var cocktailRowBinding: CocktailRowBinding) : RecyclerView.ViewHolder(cocktailRowBinding.cocktailRow) {
        var settings: Drawable? = null
        var position: Int? = 0

        fun bindCard(cocktail: Cocktail) {
            if (cocktailRowBinding.viewModel == null) {
                cocktailRowBinding.viewModel = CocktailRowVM(cocktail)
            } else {
                cocktailRowBinding.viewModel!!.setCocktail(cocktail)
            }
        }

    }

    private inner class ItemFilter : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val results = FilterResults()
            val newList = ArrayList<Cocktail>(cocktails.size)

            for (c in cocktails) {
                if (c.name.toLowerCase(Locale.getDefault()).contains(constraint) || c.category.toLowerCase(Locale.getDefault()).contains((constraint)))
                    newList.add(c)
            }
            results.values = newList
            results.count = newList.size
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            filteredCocktails = results.values as ArrayList<Cocktail>
            notifyDataSetChanged()
        }
    }
}
