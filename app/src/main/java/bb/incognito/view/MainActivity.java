package bb.incognito.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import bb.incognito.R;
import bb.incognito.databinding.ActivityMainBinding;
import bb.incognito.view.adapter.GuestAdapter;
import bb.incognito.viewModel.MainActivityVM;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainActivityBinding;
    MainActivityVM mainActivityViewModel;
    public GuestAdapter guestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
    }

    public void initDataBinding(){
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupAdapter();
        mainActivityViewModel = new MainActivityVM(guestAdapter, getFragmentManager());
        mainActivityBinding.setViewModel(mainActivityViewModel);
    }

    private void setupAdapter(){
        guestAdapter= new GuestAdapter();
        mainActivityBinding.list.setAdapter(guestAdapter);
        mainActivityBinding.list.setLayoutManager(new LinearLayoutManager(this));

    }
}
