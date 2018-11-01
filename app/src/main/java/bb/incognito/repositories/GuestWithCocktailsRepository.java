package bb.incognito.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import java.util.List;

import bb.incognito.AppDatabase;
import bb.incognito.dao.GuestWithCocktailsDao;
import bb.incognito.model.GuestWithCocktails;


public class GuestWithCocktailsRepository {
    private GuestWithCocktailsDao guestWithCocktailsDao;
    private LiveData<List<GuestWithCocktails>> allGuests;

    public GuestWithCocktailsRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        guestWithCocktailsDao = db.guestWithCocktailsDao();
        allGuests = guestWithCocktailsDao.loadGuestsAndCocktails();
    }

    public LiveData<List<GuestWithCocktails>> getAllGuests() {
        return allGuests;
    }
}