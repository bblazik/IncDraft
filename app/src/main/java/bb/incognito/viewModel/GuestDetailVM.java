package bb.incognito.viewModel;

import android.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import java.util.List;

import bb.incognito.R;
import bb.incognito.model.Cocktail;
import bb.incognito.model.Guest;
import bb.incognito.repositories.CocktailRepository;
import bb.incognito.view.AddCocktailFragment;

public class GuestDetailVM {

    private Guest guest;
    public static CocktailRepository cocktailRepository;
    FragmentManager fragmentManager;

    public GuestDetailVM(Guest guest, FragmentManager fragmentManager, CocktailRepository cocktailRepository) {
        this.guest = guest;
        this.fragmentManager = fragmentManager;
        GuestDetailVM.cocktailRepository = cocktailRepository;
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
        fragmentManager.beginTransaction().replace(R.id.container2, AddCocktailFragment.Companion.launch(guest)).addToBackStack(null).commit();
    }
}
