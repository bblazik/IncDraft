package bb.incognito.injection.module;

import bb.incognito.data.API;
import bb.incognito.data.ExampleService;
import bb.incognito.injection.scope.PerDataManager;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@Module
public class DataManagerModule {

    public DataManagerModule(){}

    @Provides
    @PerDataManager
    ExampleService provideExampleService()
    {
        return new API().getClient();
    }

    @Provides
    @PerDataManager
    Scheduler provideSubscribeScheduler()
    {
        return Schedulers.io();
    }
}
