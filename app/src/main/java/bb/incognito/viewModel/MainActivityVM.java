package bb.incognito.viewModel;

import android.databinding.BaseObservable;
import android.databinding.ObservableField;

import java.util.ArrayList;
import java.util.List;

import bb.incognito.MyApp;
import bb.incognito.data.DataManager;
import bb.incognito.model.Guest;
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

}
