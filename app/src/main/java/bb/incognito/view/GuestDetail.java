package bb.incognito.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.SearchView;

import bb.incognito.R;
import bb.incognito.databinding.GuestDetailBinding;
import bb.incognito.model.Cocktail;
import bb.incognito.model.Guest;
import bb.incognito.model.GuestWithCocktails;
import bb.incognito.repositories.CocktailRepository;
import bb.incognito.utils.SwipeToDeleteCallback;
import bb.incognito.view.adapter.CocktailAdapter;
import bb.incognito.viewModel.GuestDetailVM;

public class GuestDetail extends AppCompatActivity implements SearchView.OnQueryTextListener{

    GuestDetailBinding guestDetailBinding;
    GuestDetailVM guestDetailViewModel;
    CocktailAdapter cocktailAdapter;
    CocktailRepository cocktailRepository;
    private SearchView sv;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_detail);
        initDataBinding();
    }

    public void initDataBinding(){
        guestDetailBinding = DataBindingUtil.setContentView(this, R.layout.guest_detail);
        Guest guest = getIntent().getExtras().getParcelable("GUEST");
        cocktailRepository = new CocktailRepository(this.getApplication(), guest.getId());
        setupAdapter(guest);
        guestDetailViewModel = new GuestDetailVM(guest, getFragmentManager(), cocktailRepository);
        guestDetailBinding.setViewModel(guestDetailViewModel);
        sv = guestDetailBinding.search;
        sv.setOnQueryTextListener(this);
    }
    public static Intent launchDetail(Context context, GuestWithCocktails guest) {
        Intent intent = new Intent(context, GuestDetail.class);
        intent.putExtra("GUEST", guest);
        return intent;
    }

    private void setupAdapter(Guest guest){
        cocktailAdapter = new CocktailAdapter(); //get data of cocktails. from guest
        cocktailAdapter.setCocktailList(guest.getCocktailList());
        SwipeToDeleteCallback swipeHandler = new SwipeToDeleteCallback(getApplicationContext()) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                final Cocktail removedCocktail = cocktailAdapter.getCocktailList().get(viewHolder.getAdapterPosition());
                final int removedPosition = viewHolder.getAdapterPosition();

                cocktailAdapter.removeAt(viewHolder.getAdapterPosition());

                Snackbar snackbar = Snackbar.make(guestDetailBinding.getRoot(), "Removed cocktail: " + removedCocktail.getName(), Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cocktailAdapter.restoreItem(removedPosition, removedCocktail);
                    }
                });
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeHandler);
        itemTouchHelper.attachToRecyclerView(guestDetailBinding.list);
        guestDetailBinding.list.setAdapter(cocktailAdapter);
        guestDetailBinding.list.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        cocktailAdapter.getFilter().filter(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        cocktailAdapter.getFilter().filter(s);
        return false;
    }
}
