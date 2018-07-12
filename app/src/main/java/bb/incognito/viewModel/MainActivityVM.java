package bb.incognito.viewModel;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import bb.incognito.MyApp;
import bb.incognito.data.DataManager;
import bb.incognito.model.Guest;
import bb.incognito.view.MainActivity;
import bb.incognito.view.adapter.GuestAdapter;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivityVM extends BaseObservable {

    private DataManager dataManager;
    private static GuestAdapter guestAdapter;
    private CompositeDisposable compositeDisposable;
    public ObservableField<Boolean> progressBarVisible = new ObservableField<>();

    public MainActivityVM(GuestAdapter cardAdapter) {
        compositeDisposable = new CompositeDisposable();
        dataManager = MyApp.get().getComponent().dataManager();
        guestAdapter = cardAdapter;
        getGuestList();
    }

    public void getGuestList()
    {
        List array = new ArrayList<Guest>();
        array.add(new Guest("Zajac"));
        guestAdapter.setGuestList(array);
        notifyChange();
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
