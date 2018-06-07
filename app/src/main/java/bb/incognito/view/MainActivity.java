package bb.incognito.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import bb.incognito.R;
import bb.incognito.databinding.ActivityMainBinding;
import bb.incognito.viewModel.MainActivityVM;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainActivityBinding;
    MainActivityVM mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
    }

    public void initDataBinding(){
        mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainActivityViewModel = new MainActivityVM();
        mainActivityBinding.setVm(mainActivityViewModel);
    }
}
