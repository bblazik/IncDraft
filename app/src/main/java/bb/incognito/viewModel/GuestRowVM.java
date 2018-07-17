package bb.incognito.viewModel;

import android.databinding.BaseObservable;
import android.view.View;
import android.widget.Toast;

import bb.incognito.model.Guest;
import bb.incognito.view.GuestDetail;

public class GuestRowVM extends BaseObservable {

    private Guest guest;

    public GuestRowVM(Guest guest) {
        this.guest = guest;
    }

    public String getName(){ return guest.getName();}

    public void setGuest(Guest guest) {
        this.guest = guest;
        notifyChange();
    }

    public void onItemClick(View view) {
        view.getContext().startActivity(GuestDetail.launchDetail(view.getContext(), guest));
    }
}
