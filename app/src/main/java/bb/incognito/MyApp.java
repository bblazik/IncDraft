package bb.incognito;

import android.app.Application;
import android.app.FragmentManager;
import android.content.Context;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class MyApp extends Application {
    private Scheduler scheduler;
    public static Context mContext;
    public static FragmentManager fragmentManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static MyApp get(Context context) {
        mContext = context;
        return (MyApp) context.getApplicationContext();
    }

    public static MyApp get() {
        return (MyApp) mContext.getApplicationContext();
    }

}