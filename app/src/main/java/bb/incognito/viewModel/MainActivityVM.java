package bb.incognito.viewModel;

import android.app.Application;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.design.widget.TabLayout;
import android.view.View;
import java.util.List;

import bb.incognito.model.GuestWithCocktails;
import bb.incognito.repositories.GuestRepository;
import bb.incognito.repositories.GuestWithCocktailsRepository;
import bb.incognito.view.AddGuestFragment;
import bb.incognito.view.GuestDetail;

public class MainActivityVM extends AndroidViewModel{
    private GuestRepository guestRepository;
    private GuestWithCocktailsRepository guestWithCocktailsRepository;
    private LiveData<List<GuestWithCocktails>> allGuests;
    private FragmentManager fragmentManager;

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public MainActivityVM(Application application) {
        super(application);
        guestRepository = new GuestRepository(application);
        guestWithCocktailsRepository = new GuestWithCocktailsRepository(application);
        allGuests = guestWithCocktailsRepository.getAllGuests();
    }

    public void onClick(View view) {
        DialogFragment newFragment = AddGuestFragment.newInstance(false,-1, guestRepository);
        newFragment.show(fragmentManager, "New dialog");
    }

    public LiveData<List<GuestWithCocktails>> getAllGuests() {
        return allGuests;
    }
}
