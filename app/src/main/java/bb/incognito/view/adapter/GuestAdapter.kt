package bb.incognito.view.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable

import java.util.ArrayList

import bb.incognito.R
import bb.incognito.viewModel.GuestRowVM
import bb.incognito.databinding.GuestRowBinding
import bb.incognito.model.Guest
import bb.incognito.model.GuestWithCocktails

class GuestAdapter : RecyclerView.Adapter<GuestAdapter.GuestViewHolder>(), Filterable {
    private var guests: MutableList<GuestWithCocktails>? = null
    private var filteredGuests: MutableList<GuestWithCocktails>? = null
    private val filter = ItemFilter()

    private val callback = object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            val dragFlags = 0//ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
            return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
        }

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            //val g = filteredGuests!![viewHolder.adapterPosition]
        }
    }

    var itemTouchHelper = ItemTouchHelper(callback)

    fun getGuests(): MutableList<GuestWithCocktails> {
        return guests!!
    }
    fun setGuests(guests: MutableList<GuestWithCocktails>?) {
        this.guests = guests
        this.filteredGuests = guests
        notifyDataSetChanged()
    }

    fun restoreItem(removedPosition: Int, guestWithCocktails: GuestWithCocktails) {
        guests!!.add(removedPosition, guestWithCocktails)
        notifyItemInserted(removedPosition)
    }

    fun removeAt(position: Int) {
        guests!!.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val cardRowBinding = DataBindingUtil.inflate<GuestRowBinding>(LayoutInflater.from(parent.context), R.layout.guest_row,
                parent, false)
        return GuestViewHolder(cardRowBinding)
    }

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        if (filteredGuests != null) {
            holder.bindCard(filteredGuests!![position])
            holder.itemView.setOnContextClickListener { _ ->
                itemTouchHelper.startSwipe(holder)
                false
            }
        } else {
            holder.bindCard(GuestWithCocktails(Guest("Nie ma gości :(")))
        }
    }

    override fun getItemCount(): Int {
        return if (filteredGuests != null) filteredGuests!!.size else 0
    }

    override fun getFilter(): Filter {
        return filter
    }

    inner class GuestViewHolder(var guestRowBinding: GuestRowBinding) : RecyclerView.ViewHolder(guestRowBinding.guestRow) {

        fun bindCard(guest: GuestWithCocktails) {
            if (guestRowBinding.viewModel == null) {
                guestRowBinding.viewModel = GuestRowVM(guest)
            } else {
                guestRowBinding.viewModel?.setGuest(guest)
            }
        }
    }

    private inner class ItemFilter : Filter() {
        override fun performFiltering(constraint: CharSequence): Filter.FilterResults {
            val results = Filter.FilterResults()
            val newList = ArrayList<GuestWithCocktails>(guests!!.size)

            for (g in guests!!) {
                if (g.name.toLowerCase().contains(constraint))
                    newList.add(g)
            }
            results.values = newList
            results.count = newList.size
            return results
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
            filteredGuests = results.values as ArrayList<GuestWithCocktails>
            notifyDataSetChanged()
        }
    }
}
