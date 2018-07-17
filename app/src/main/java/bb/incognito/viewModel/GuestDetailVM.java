package bb.incognito.viewModel;

import android.view.View;
import android.widget.Toast;

import java.util.List;

import bb.incognito.MyApp;
import bb.incognito.model.Cocktail;
import bb.incognito.model.Guest;
import bb.incognito.view.GuestDetail;

public class GuestDetailVM {

    private Guest guest;

    public GuestDetailVM(Guest guest) {
        this.guest = guest;
    }
    public String getName()
    {
        return guest.getName();
    }
    public String getDiscount(){ return String.valueOf(guest.getDiscount());}
    public void setDiscount(String discount){guest.setDiscount(Float.valueOf(discount));}
    public String getNotes(){return guest.getNotes();}
    public void setNotes(String note) {guest.setNotes(note);}
    public List<Cocktail> getCocktailList()
    {
        return guest.getCocktailList();
    }

    public void onClick(View view) {
        //view.getContext().startActivity(GuestDetail.launchDetail(view.getContext(), guest));
        Toast.makeText(view.getContext(), "Note and location cannot be empty", Toast.LENGTH_SHORT).show();
    }
}
