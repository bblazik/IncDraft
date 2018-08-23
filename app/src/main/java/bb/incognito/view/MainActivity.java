package bb.incognito.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import bb.incognito.MyApp;
import bb.incognito.R;
import bb.incognito.databinding.ActivityMainBinding;
import bb.incognito.model.Guest;
import bb.incognito.view.adapter.GuestAdapter;
import bb.incognito.viewModel.MainActivityVM;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainActivityBinding;
    private MainActivityVM mainActivityViewModel;
    private GuestAdapter guestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityVM.class);
        mainActivityViewModel.setFragmentManager(this.getFragmentManager());
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainActivityBinding.setViewModel(mainActivityViewModel);
        mainActivityBinding.setLifecycleOwner(this);


        guestAdapter= new GuestAdapter();
        mainActivityBinding.list.setAdapter(guestAdapter);
        mainActivityBinding.list.setLayoutManager(new LinearLayoutManager(this));


        mainActivityViewModel.getAllGuests().observe(this, new Observer<List<Guest>>() {
            @Override
            public void onChanged(@Nullable List<Guest> guests) {
                guestAdapter.setGuests(guests);
            }
        });


    }


}
