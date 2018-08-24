package bb.incognito.viewModel;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import bb.incognito.MyApp;
import bb.incognito.model.Cocktail;
import bb.incognito.model.Guest;
import bb.incognito.repositories.CocktailRepository;
import bb.incognito.view.AddCocktailFragment;
import bb.incognito.view.AddGuestFragment;
import bb.incognito.view.GuestDetail;
import bb.incognito.view.adapter.CocktailAdapter;
import bb.incognito.view.adapter.GuestAdapter;

public class GuestDetailVM {

    private Guest guest;
    public static CocktailRepository cocktailRepository;
    FragmentManager fragmentManager;

    public GuestDetailVM(Guest guest, FragmentManager fragmentManager, CocktailRepository cocktailRepository) {
        this.guest = guest;
        this.fragmentManager = fragmentManager;
        this.cocktailRepository = cocktailRepository;
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
        DialogFragment newFragment = AddCocktailFragment.newInstance(false,-1, cocktailRepository, guest);
        newFragment.show(fragmentManager, "New dialog");
    }
}
