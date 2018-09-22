package bb.incognito.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.adapters.AdapterViewBindingAdapter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import bb.incognito.R;
import bb.incognito.model.Guest;
import bb.incognito.model.GuestWithCocktails;
import bb.incognito.repositories.GuestWithCocktailsRepository;
import bb.incognito.viewModel.GuestRowVM;
import bb.incognito.databinding.GuestRowBinding;

public class GuestAdapter extends RecyclerView.Adapter<GuestAdapter.GuestViewHolder> implements Filterable {
    private GuestWithCocktailsRepository guestWithCocktailsRepository;
    private List<GuestWithCocktails> guests;
    private List<GuestWithCocktails> filteredGuests;
    private ItemFilter filter = new ItemFilter();

    private ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            final int dragFlags = 0;//ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            GuestWithCocktails g =  filteredGuests.get(viewHolder.getAdapterPosition());

        }
    };

    public ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);

    public void setGuests(List<GuestWithCocktails> guests) {
        this.guests = guests;
        this.filteredGuests = guests;
        notifyDataSetChanged();
    }

    @Override
    public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GuestRowBinding cardRowBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.guest_row,
                        parent, false);
        return new GuestViewHolder(cardRowBinding);
    }

    @Override
    public void onBindViewHolder(GuestViewHolder holder, int position) {
        if (filteredGuests != null)
        {
            holder.bindCard(filteredGuests.get(position));
            holder.itemView.setOnContextClickListener(view -> {
                itemTouchHelper.startSwipe(holder);
                return false;
            });
        }
        else
        {
            holder.bindCard(new GuestWithCocktails(new Guest("Nie ma gości :(")));
        }
    }

    @Override
    public int getItemCount() {
        return filteredGuests != null ? filteredGuests.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    class GuestViewHolder extends RecyclerView.ViewHolder{
        GuestRowBinding guestRowBinding;


        public GuestViewHolder(GuestRowBinding guestRowBinding) {
            super(guestRowBinding.guestRow);
            this.guestRowBinding = guestRowBinding;
        }

        void bindCard(GuestWithCocktails guest) {
            if (guestRowBinding.getViewModel() == null) {
                guestRowBinding.setViewModel(
                        new GuestRowVM(guest));
            } else {
                guestRowBinding.getViewModel().setGuest(guest);
            }
        }
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<GuestWithCocktails> newList = new ArrayList<>(guests.size());

            for(GuestWithCocktails g : guests)
            {
                if(g.getName().toLowerCase().contains(constraint))
                    newList.add(g);
            }
            results.values = newList;
            results.count = newList.size();
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredGuests = (ArrayList<GuestWithCocktails>) results.values;
            notifyDataSetChanged();
        }
    }
}
