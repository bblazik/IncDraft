package bb.incognito.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import bb.incognito.AppDatabase;
import bb.incognito.dao.GuestCocktailJoinDao;
import bb.incognito.dao.GuestDao;
import bb.incognito.dao.GuestWithCocktailsDao;
import bb.incognito.model.Cocktail;
import bb.incognito.model.Guest;
import bb.incognito.model.GuestCocktailJoin;
import bb.incognito.model.GuestWithCocktails;

public class GuestWithCocktailsRepository {
    private GuestWithCocktailsDao guestWithCocktailsDao;
    private GuestCocktailJoinDao guestCocktailJoinDao;
    private LiveData<List<GuestWithCocktails>> allGuests;
    private  LiveData<List<Cocktail>> cocktailsForGuest;

    public GuestWithCocktailsRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        guestWithCocktailsDao = db.guestWithCocktailsDao();
        guestCocktailJoinDao = db.guestCocktailJoinDao();
        allGuests = guestWithCocktailsDao.loadGuestsAndCocktails();
    }

    public LiveData<List<GuestWithCocktails>> getAllGuests() {
        return allGuests;
    }

    public LiveData<List<Cocktail>> getCocktails(int guestId)
    {
        return guestCocktailJoinDao.getCocktailsForUsers(guestId);
    }

    public void insertRelation(GuestCocktailJoin guestCocktailJoin)
    {
        new GuestWithCocktailsRepository.insertAsyncTask(guestCocktailJoinDao).execute(guestCocktailJoin);
    }

    private static class insertAsyncTask extends AsyncTask<GuestCocktailJoin, Void, Void> {
        private GuestCocktailJoinDao guestCocktailJoinDao;

        insertAsyncTask(GuestCocktailJoinDao guestCocktailJoinDao) {
            this.guestCocktailJoinDao = guestCocktailJoinDao;
        }

        @Override
        protected Void doInBackground(final GuestCocktailJoin... params) {
            guestCocktailJoinDao.insert(params[0]);
            return null;
        }
    }

}