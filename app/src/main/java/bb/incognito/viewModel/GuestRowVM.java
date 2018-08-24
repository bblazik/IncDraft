package bb.incognito.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.databinding.BaseObservable;
import android.view.View;
import android.widget.Toast;

import bb.incognito.model.Guest;
import bb.incognito.view.GuestDetail;

public class GuestRowVM {

    private Guest guest;

    public GuestRowVM(Guest guest) {
        this.guest = guest;
    }

    public String getName(){ return guest.getName();}

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public void onItemClick(View view) {
        view.getContext().startActivity(GuestDetail.launchDetail(view.getContext(), guest));
    }
}
