package bb.incognito.data;

import bb.incognito.MyApp;
import bb.incognito.injection.component.DaggerDataManagerComponent;
import bb.incognito.injection.module.DataManagerModule;
import io.reactivex.Scheduler;
import android.content.Context;
import javax.inject.Inject;

public class DataManager {
    @Inject
    protected ExampleService exampleService;
    @Inject protected Scheduler mSubscribeScheduler;

    public DataManager(Context context){
        injectDependencies(context);
    }

    public DataManager(ExampleService service, Scheduler subscribeScheduler){
        exampleService = service;
        mSubscribeScheduler = subscribeScheduler;
    }

    protected void injectDependencies(Context context){ //todo remove param
        DaggerDataManagerComponent.builder()
                .applicationComponent(MyApp.get(context).getComponent())
                .dataManagerModule(new DataManagerModule())
                .build()
                .inject(this);
    }

    public Scheduler getScheduler(){
        return mSubscribeScheduler;
    }

//                              EXAMPLE
//    public Observable<Deck> getShuffleDeck(String deckId){
//        return mDeckOfCardsService.getShuffle(deckId)
//                .concatMap(new Function<Deck, ObservableSource<? extends Deck>>() {
//                    @Override
//                    public ObservableSource<? extends Deck> apply(@NonNull Deck deck) throws Exception {
//                        return deck != null ? Observable.just(deck): Observable.<Deck>empty();
//                    }
//                });
//    }
}