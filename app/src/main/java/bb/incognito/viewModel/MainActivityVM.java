package bb.incognito.viewModel;

import android.app.Application;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;

import java.util.List;

import bb.incognito.model.GuestWithCocktails;
import bb.incognito.repositories.GuestRepository;
import bb.incognito.repositories.GuestWithCocktailsRepository;
import bb.incognito.view.AddGuestFragment;

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

    @BindingAdapter("swipe")
    static public void enableSwipe(SwipeRefreshLayout swipeRefreshLayout, SwipeRefreshLayout.OnRefreshListener ls){
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setOnRefreshListener(ls);
    }

    public SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            getAllGuests();
        }
    };

    public LiveData<List<GuestWithCocktails>> getAllGuests() {
        return allGuests;
    }
}
