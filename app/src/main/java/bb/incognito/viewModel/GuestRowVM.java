package bb.incognito.viewModel;

import android.view.View;

import bb.incognito.model.GuestWithCocktails;
import bb.incognito.view.GuestDetail;

public class GuestRowVM {

    private GuestWithCocktails guest;

    public GuestRowVM(GuestWithCocktails guest) {
        this.guest = guest;
    }

    public String getName(){ return guest.getName();}

    public void setGuest(GuestWithCocktails guest) {
        this.guest = guest;
    }

    public void onItemClick(View view) {
        view.getContext().startActivity(GuestDetail.launchDetail(view.getContext(), guest));
    }
}
