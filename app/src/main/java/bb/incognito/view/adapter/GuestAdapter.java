package bb.incognito.view.adapter;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import bb.incognito.R;
import bb.incognito.model.Cocktail;
import bb.incognito.model.Guest;
import bb.incognito.model.GuestWithCocktails;
import bb.incognito.viewModel.GuestRowVM;
import bb.incognito.databinding.GuestRowBinding;

public class GuestAdapter extends RecyclerView.Adapter<GuestAdapter.GuestViewHolder>{
    private List<GuestWithCocktails> guests;

    public void setGuests(List<GuestWithCocktails> guests) {
        this.guests = guests;
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
        if (guests != null) {
            holder.bindCard(guests.get(position));
        } else {
            holder.bindCard(new GuestWithCocktails(new Guest("Nie ma gości :(")));
        }
    }

    @Override
    public int getItemCount() {
        if (guests != null) {
            return guests.size();
        } else {
            return 0;
        }
    }



    class GuestViewHolder extends RecyclerView.ViewHolder {
        GuestRowBinding guestRowBinding;
        Drawable settings;

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
        public void onItemSelected() {
            settings = itemView.getBackground();
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        public void onItemClear() {
            itemView.setBackground(settings);
        }
    }
}
