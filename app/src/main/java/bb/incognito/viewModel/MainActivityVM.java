package bb.incognito.viewModel;

import android.app.Application;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.ComponentName;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.SearchView;
import java.util.List;

import bb.incognito.MyApp;
import bb.incognito.model.Guest;
import bb.incognito.repositories.GuestRepository;
import bb.incognito.view.AddGuestFragment;
import bb.incognito.view.MainActivity;

import static bb.incognito.MyApp.fragmentManager;

public class MainActivityVM extends AndroidViewModel {
    private GuestRepository guestRepository;
    private LiveData<List<Guest>> allGuests;

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    private FragmentManager fragmentManager;

    public MainActivityVM(Application application) {
        super(application);
        guestRepository = new GuestRepository(application);
        allGuests = guestRepository.getAllGuests();
    }

    public LiveData<List<Guest>> getAllGuests() {
        return allGuests;
    }

    public void onClick(View view) {
        DialogFragment newFragment = AddGuestFragment.newInstance(false,-1, guestRepository);
        newFragment.show(fragmentManager, "New dialog");
    }

    @BindingAdapter("enableSearchManager")
    static public void enableSearchManager(SearchView searchView, boolean b){
        SearchManager searchManager = (SearchManager) MyApp.mContext.getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(MyApp.mContext, MainActivity.class)));
    }

    @BindingAdapter("enableQueryListener")
    static public void enableQueryListener(SearchView searchView, boolean b){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                guestAdapter.filterByName(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                guestAdapter.filterByName(newText);
                return false;
            }
        });
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
}
