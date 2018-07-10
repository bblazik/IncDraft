package bb.incognito.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import bb.incognito.R;
import bb.incognito.databinding.GuestDetailBinding;
import bb.incognito.model.Guest;
import bb.incognito.viewModel.GuestDetailVM;

public class GuestDetail extends AppCompatActivity {

    GuestDetailBinding guestDetailBinding;
    GuestDetailVM guestDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_detail);
        initDataBinding();
    }

    public void initDataBinding(){
        guestDetailBinding = DataBindingUtil.setContentView(this, R.layout.guest_detail);
        Guest guest = getIntent().getExtras().getParcelable("GUEST");
        guestDetailViewModel = new GuestDetailVM(guest);
        guestDetailBinding.setViewModel(guestDetailViewModel);
    }
    public static Intent launchDetail(Context context, Guest guest) {
        Intent intent = new Intent(context, GuestDetail.class);
        intent.putExtra("GUEST", guest);
        return intent;
    }
}
