package bb.incognito.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
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

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }


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
        guestAdapter.itemTouchHelper.attachToRecyclerView(mainActivityBinding.list);
        mainActivityBinding.list.setAdapter(guestAdapter);
        mainActivityBinding.list.setLayoutManager(new LinearLayoutManager(this));
        sv = mainActivityBinding.search;

        mainActivityBinding.tabLayout.addOnTabSelectedListener(t);
    }

    TabLayout.OnTabSelectedListener t = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition())
            {
                case 0: return;
                case 1:  getBaseContext().startActivity(new Intent(getBaseContext(), CocktailListActivity.class));
            }
        }
        @Override
        public void onTabUnselected(TabLayout.Tab tab) {}
        @Override
        public void onTabReselected(TabLayout.Tab tab) {}
    };

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
