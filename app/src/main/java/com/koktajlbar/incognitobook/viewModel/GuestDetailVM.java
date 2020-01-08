package com.koktajlbar.incognitobook.viewModel;

import androidx.fragment.app.FragmentManager;
import android.view.View;

import com.koktajlbar.incognitobook.R;
import com.koktajlbar.incognitobook.model.GuestWithCocktails;
import com.koktajlbar.incognitobook.repositories.CocktailRepository;
import com.koktajlbar.incognitobook.view.AddCocktailFragment;

public class GuestDetailVM {

    private GuestWithCocktails guest;
    public static CocktailRepository cocktailRepository;
    FragmentManager fragmentManager;

    public GuestDetailVM(GuestWithCocktails guest, FragmentManager fragmentManager, CocktailRepository cocktailRepository) {
        this.guest = guest;
        this.fragmentManager = fragmentManager;
        GuestDetailVM.cocktailRepository = cocktailRepository;
    }

    public String getName()
    {
        return guest.getName();
    }
    public String getDiscount(){ return String.valueOf(guest.getGuest().getDiscount());}
    public void setDiscount(String discount){
        guest.getGuest().setDiscount(Float.valueOf(discount));}
    public String getNotes(){return guest.getGuest().getNotes();}
    public void setNotes(String note) {
        guest.getGuest().setNotes(note);}

    public void onClick(View view) {
        fragmentManager.beginTransaction().replace(R.id.container2, AddCocktailFragment.Companion.launch(guest)).addToBackStack(null).commit();
    }
}
