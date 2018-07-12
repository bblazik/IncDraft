package bb.incognito.view.adapter;

import android.databinding.DataBindingUtil;
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
//        originalData = displayList;
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

    class GuestViewHolder extends RecyclerView.ViewHolder {
        GuestRowBinding guestRowBinding;

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
    }
}
