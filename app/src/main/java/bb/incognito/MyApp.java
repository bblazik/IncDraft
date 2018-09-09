package bb.incognito;

import android.app.Application;
import android.app.FragmentManager;
import android.content.Context;

import bb.incognito.injection.component.ApplicationComponent;
import bb.incognito.injection.component.DaggerApplicationComponent;
import bb.incognito.injection.module.ApplicationModule;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class MyApp extends Application {
    ApplicationComponent mApplicationComponent;
    private Scheduler scheduler;
    public static Context mContext;
    public static FragmentManager fragmentManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mContext = getApplicationContext();
    }

    public static MyApp get(Context context) {
        mContext = context;
        return (MyApp) context.getApplicationContext();
    }

    public static MyApp get() {
        return (MyApp) mContext.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }

    public Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }
        return scheduler;
    }
}