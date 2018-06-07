package bb.incognito.injection.component;

import android.app.Application;
import javax.inject.Singleton;
import bb.incognito.data.DataManager;
import bb.incognito.injection.module.ApplicationModule;
import bb.incognito.view.MainActivity;
import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);

    Application application();
    DataManager dataManager();
}