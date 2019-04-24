package com.koktajlbar.incognitobook.viewModel;

import android.databinding.BaseObservable;
import android.view.View;
import android.widget.Toast;

import com.koktajlbar.incognitobook.model.GuestWithCocktails;
import com.koktajlbar.incognitobook.view.GuestDetail;

public class GuestRowVM extends BaseObservable
{
    private GuestWithCocktails guest;

    public GuestRowVM(GuestWithCocktails guest) {
        this.guest = guest;
    }

    public String getName(){ return guest.getName();}

    public void setName(String s){guest.setName(s);}

    public void setGuest(GuestWithCocktails guest) {
        this.guest = guest;
        notifyChange();
    }

    public void onItemClick(View view) {
        view.getContext().startActivity(GuestDetail.Companion.launchDetail(view.getContext(), guest));
    }

    public boolean onLongClick(View view)
    {
        Toast.makeText(view.getContext(), "ddd", Toast.LENGTH_SHORT);
        return true;
    }
}
