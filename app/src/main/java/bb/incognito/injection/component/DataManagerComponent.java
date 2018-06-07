package bb.incognito.injection.component;
import bb.incognito.data.DataManager;
import bb.incognito.injection.module.DataManagerModule;
import bb.incognito.injection.scope.PerDataManager;
import dagger.Component;

@PerDataManager
@Component(dependencies = ApplicationComponent.class, modules = DataManagerModule.class)
public interface DataManagerComponent{

    void inject (DataManager dataManager);
}
