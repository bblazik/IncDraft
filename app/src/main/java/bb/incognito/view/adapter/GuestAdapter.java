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
import bb.incognito.model.Guest;
import bb.incognito.viewModel.GuestRowVM;
import bb.incognito.databinding.GuestRowBinding;

public class GuestAdapter extends RecyclerView.Adapter<GuestAdapter.GuestViewHolder>{

    private List<Guest> displayList = new ArrayList<>();
    private List<Guest> originalData = new ArrayList<>();

    public void setGuestList(List<Guest> guestList) {
        displayList = guestList;
        originalData = displayList;
        notifyDataSetChanged();
    }
    public void addGuestToList(Guest guest){
        displayList.add(guest);
        //originalData.add(guest);
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
        holder.bindCard(displayList.get(position));
    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }

    public int filterByName(String query){ //Can be replaced with filter interface. But idk why would I.
        List<Guest> filteredList = new ArrayList<>();
        query = query.replaceAll("\\s+","").toLowerCase();
        for(Guest c : originalData) { // due to lack of lambda in api<24
            if(guestNameContains(c, query)){
                filteredList.add(c);
            }
        }
        displayList = filteredList;
        notifyDataSetChanged();
        return displayList.size();
    }

    boolean guestNameContains(Guest g, String query){
        return g.getName().toLowerCase().contains(query);
    }

    class GuestViewHolder extends RecyclerView.ViewHolder {
        GuestRowBinding guestRowBinding;
        Drawable settings;

        public GuestViewHolder(GuestRowBinding guestRowBinding) {
            super(guestRowBinding.guestRow);
            this.guestRowBinding = guestRowBinding;
        }

        void bindCard(Guest guest) {
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
