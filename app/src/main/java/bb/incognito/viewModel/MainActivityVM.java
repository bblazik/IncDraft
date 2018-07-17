package bb.incognito.viewModel;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bb.incognito.MyApp;
import bb.incognito.data.DataManager;
import bb.incognito.model.Guest;
import bb.incognito.view.AddGuestFragment;
import bb.incognito.view.MainActivity;
import bb.incognito.view.adapter.GuestAdapter;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivityVM extends BaseObservable {

    private DataManager dataManager;
    private static GuestAdapter guestAdapter;
    private CompositeDisposable compositeDisposable;
    public ObservableField<Boolean> progressBarVisible = new ObservableField<>();
    FragmentManager fragmentManager;

    public MainActivityVM(GuestAdapter cardAdapter, FragmentManager fragmentManager) {
        compositeDisposable = new CompositeDisposable();
        dataManager = MyApp.get().getComponent().dataManager();
        guestAdapter = cardAdapter;
        this.fragmentManager = fragmentManager;
        getGuestList();
    }

    public void getGuestList()
    {
        List array = new ArrayList<Guest>();
        array.add(new Guest("Zajac"));
        array.add(new Guest("Zajac"));
        array.add(new Guest("Zajac"));
        array.add(new Guest("Zajac"));
        array.add(new Guest("Zajac"));
        array.add(new Guest("Zajac"));
        array.add(new Guest("Zajac"));
        array.add(new Guest("NieZajac"));
        guestAdapter.setGuestList(array);
        notifyChange();
    }

    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Note and location cannot be empty", Toast.LENGTH_SHORT).show();
        //guestAdapter.addGuestToList();
        DialogFragment newFragment = AddGuestFragment.newInstance(false,-1, guestAdapter);
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
                guestAdapter.filterByName(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                guestAdapter.filterByName(newText);
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
            getGuestList();
        }
    };
}
