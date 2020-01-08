package com.koktajlbar.incognitobook.view.adapter

import androidx.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable

import java.util.ArrayList

import com.koktajlbar.incognitobook.R
import com.koktajlbar.incognitobook.databinding.CocktailRowBinding
import com.koktajlbar.incognitobook.model.Cocktail
import com.koktajlbar.incognitobook.viewModel.CocktailRowVM

class CocktailAdapter : RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder>(), Filterable {
    private var cocktails: MutableList<Cocktail> = ArrayList()
    private var filteredCocktails: MutableList<Cocktail> = ArrayList()
    private val filter = ItemFilter()
    var checkable = false
    internal var checkedCocktails: MutableList<Cocktail> = ArrayList()

    var cocktailList: MutableList<Cocktail>
        get() = cocktails
        set(cocktailList) {
            cocktails = cocktailList
            filteredCocktails = cocktailList
            notifyDataSetChanged()
        }
    fun getCheckedCocktails(): List<Cocktail> {
        return checkedCocktails
    }

    fun restoreItem(removedPosition: Int, removedCocktail: Cocktail) {
        cocktails.add(removedPosition, removedCocktail)
        notifyItemInserted(removedPosition)
    }

    fun removeAt(position: Int) {
        filteredCocktails.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailAdapter.CocktailViewHolder {
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

    override fun onBindViewHolder(holder: CocktailAdapter.CocktailViewHolder, position: Int) {
        holder.bindCard(filteredCocktails[position])
        holder.cocktailRowBinding.checkBox.setOnCheckedChangeListener { compoundButton, b ->
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
        val isChecked: Boolean
            get() = cocktailRowBinding.checkBox.isChecked

        fun bindCard(cocktail: Cocktail) {
            if (cocktailRowBinding.viewModel == null) {
                cocktailRowBinding.viewModel = CocktailRowVM(cocktail)
            } else {
                cocktailRowBinding!!.viewModel!!.setCocktail(cocktail)
            }
        }

        fun onItemSelected() {
            settings = itemView.background
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        fun onItemClear() {
            itemView.background = settings
        }
    }

    private inner class ItemFilter : Filter() {
        override fun performFiltering(constraint: CharSequence): Filter.FilterResults {
            val results = Filter.FilterResults()
            val newList = ArrayList<Cocktail>(cocktails.size)

            for (c in cocktails) {
                if (c.name.toLowerCase().contains(constraint) || c.category.toLowerCase().contains((constraint)))
                    newList.add(c)
            }
            results.values = newList
            results.count = newList.size
            return results
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
            filteredCocktails = results.values as ArrayList<Cocktail>
            notifyDataSetChanged()
        }
    }
}
