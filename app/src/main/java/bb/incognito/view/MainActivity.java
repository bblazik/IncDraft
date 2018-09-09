package bb.incognito.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.SearchView;

import java.util.List;

import bb.incognito.R;
import bb.incognito.databinding.ActivityMainBinding;
import bb.incognito.model.GuestWithCocktails;
import bb.incognito.view.adapter.GuestAdapter;
import bb.incognito.viewModel.MainActivityVM;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private ActivityMainBinding mainActivityBinding;
    private MainActivityVM mainActivityViewModel;
    private GuestAdapter guestAdapter;
    private SearchView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityVM.class);
        mainActivityViewModel.setFragmentManager(this.getFragmentManager());
        setMainActivityBinding();

        sv.setOnQueryTextListener(this);
        mainActivityViewModel.getAllGuests().observe(this,
                guestWithCocktails -> guestAdapter.setGuests(guestWithCocktails));
    }

    void setMainActivityBinding()
    {
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainActivityBinding.setViewModel(mainActivityViewModel);
        mainActivityBinding.setLifecycleOwner(this);

        guestAdapter= new GuestAdapter();
        mainActivityBinding.list.setAdapter(guestAdapter);
        mainActivityBinding.list.setLayoutManager(new LinearLayoutManager(this));
        sv = mainActivityBinding.search;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        guestAdapter.getFilter().filter(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        guestAdapter.getFilter().filter(s);
        return false;
    }
}
