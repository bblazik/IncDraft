package bb.incognito.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import bb.incognito.R;
import bb.incognito.databinding.GuestDetailBinding;
import bb.incognito.model.Guest;
import bb.incognito.model.GuestWithCocktails;
import bb.incognito.repositories.CocktailRepository;
import bb.incognito.view.adapter.CocktailAdapter;
import bb.incognito.viewModel.GuestDetailVM;

public class GuestDetail extends AppCompatActivity {

    GuestDetailBinding guestDetailBinding;
    GuestDetailVM guestDetailViewModel;
    CocktailAdapter cocktailAdapter;
    CocktailRepository cocktailRepository;

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
    }
    public static Intent launchDetail(Context context, GuestWithCocktails guest) {
        Intent intent = new Intent(context, GuestDetail.class);
        intent.putExtra("GUEST", guest);
        return intent;
    }

    private void setupAdapter(Guest guest){
        cocktailAdapter= new CocktailAdapter(); //get data of cocktails. from guest
        cocktailAdapter.setCocktailList(guest.getCocktailList());
        guestDetailBinding.list.setAdapter(cocktailAdapter);
        guestDetailBinding.list.setLayoutManager(new LinearLayoutManager(this));
    }
}
