package bb.incognito.viewModel;

import android.databinding.BaseObservable;
import android.view.View;
import android.widget.Toast;

import bb.incognito.model.Guest;
import bb.incognito.repositories.GuestRepository;
import bb.incognito.view.AddGuestFragment;

public class AddGuestVM extends BaseObservable {

    GuestRepository guestRepository;
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

    public AddGuestVM(AddGuestFragment dialogFragment, boolean editGuest, int pos, GuestRepository guestRepository){
        position = pos;
        edit = editGuest;
        this.guestRepository = guestRepository;
        this.df = dialogFragment;
    }

    public View.OnClickListener acceptButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(fieldsNotEmpty()) {
                Guest newGuest = new Guest(name);
                guestRepository.insert(newGuest);
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
