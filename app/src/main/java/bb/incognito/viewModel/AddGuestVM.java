package bb.incognito.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import bb.incognito.model.Guest;
import bb.incognito.view.AddGuestFragment;
import bb.incognito.view.adapter.GuestAdapter;

public class AddGuestVM extends BaseObservable {

    GuestAdapter guestAdapter;
    AddGuestFragment df;

    public String name = "";
    public String notes = "";
    public float discount = 0f;
    boolean edit = false;
    int position = -1;

    public void setDiscount(int discount)
    {
        this.discount = Float.valueOf(discount);
        notifyChange();
    }

    public int getDiscount(){return (int)discount;}
    public String getStringDiscount(){return String.valueOf(discount);}

    public AddGuestVM(AddGuestFragment dialogFragment, boolean editGuest, int pos, GuestAdapter guestAdapter){
        position = pos;
        edit = editGuest;
        this.guestAdapter = guestAdapter;
        this.df = dialogFragment;
        //For edit.
//        if(position >=0) {
//            edit = GuestAdapter.getInstance().getGuestAtPos(position);
//            location = edit.location;
//            guest = edit.guest;
//        }
    }

    public View.OnClickListener acceptButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            if(edit){
//                Guest newGuest = new Guest(location, guest);
//                GuestAdapter.getInstance().editItemOnPos(position, newGuest);
//                //GuestAdapter.getInstance().addGuestToList(newGuest);
//                df.dismiss();
//                return;
//            }

            if(fieldsNotEmpty()) {
                Guest newGuest = new Guest(name, discount, notes);
                guestAdapter.addGuestToList(newGuest);
                df.dismiss();
            }else {
                Toast.makeText(v.getContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        }
    };

    public View.OnClickListener closeButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            df.dismiss();
        }
    };

    public boolean fieldsNotEmpty(){
        return !name.equals("");
    }
}
