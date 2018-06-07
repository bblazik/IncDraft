package bb.incognito.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import bb.incognito.R;
import bb.incognito.model.Guest;

public class GuestDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_detail);
    }

    public static Intent launchDetail(Context context, Guest guest) {
        Intent intent = new Intent(context, GuestDetail.class);
        return intent;
    }
}
