package bb.incognito.viewModel;

import java.util.ArrayList;
import java.util.List;

import bb.incognito.model.Coctail;
import bb.incognito.model.Guest;

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
    public List<Coctail> getCocktailList()
    {
        return guest.getCocktailList();
    }
}
