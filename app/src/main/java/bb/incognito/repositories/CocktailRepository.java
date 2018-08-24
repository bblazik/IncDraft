package bb.incognito.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import bb.incognito.AppDatabase;
import bb.incognito.dao.CocktailDao;
import bb.incognito.model.Cocktail;
import bb.incognito.model.Guest;

public class CocktailRepository {
    private CocktailDao cocktailDao;

    public LiveData<List<Cocktail>> getCocktailsForGuest() {
        return cocktailsForGuest;
    }

    private LiveData<List<Cocktail>> cocktailsForGuest;

    public CocktailRepository(Application application, int guest_id) {
        AppDatabase db = AppDatabase.getDatabase(application);
        cocktailDao = db.cocktailDao();
        cocktailsForGuest = cocktailDao.getCocktailsForGuest(guest_id);
    }

    public void insert(Cocktail cocktail) {
        new insertAsyncTask(cocktailDao).execute(cocktail);
    }

    private static class insertAsyncTask extends AsyncTask<Cocktail, Void, Void> {
        private CocktailDao cocktailDao;

        insertAsyncTask(CocktailDao guestDao) {
            this.cocktailDao = guestDao;
        }

        @Override
        protected Void doInBackground(final Cocktail... params) {
            cocktailDao.insertCocktail(params[0]);
            return null;
        }
    }
}
