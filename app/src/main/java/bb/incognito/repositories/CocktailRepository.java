package bb.incognito.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import bb.incognito.AppDatabase;
import bb.incognito.dao.CocktailDao;
import bb.incognito.model.Cocktail;
import bb.incognito.model.Guest;

public class CocktailRepository {
    private CocktailDao cocktailDao;
    public LiveData<List<Cocktail>> getAllCocktails() { return allCocktails;}
    private LiveData<List<Cocktail>> allCocktails;

    public CocktailRepository(Application application, int guest_id) {
        AppDatabase db = AppDatabase.getDatabase(application);
        cocktailDao = db.cocktailDao();
    }

    public CocktailRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        cocktailDao = db.cocktailDao();
        allCocktails = cocktailDao.getAll();
    }

    public LiveData<List<Cocktail>> getCocktailsForGuest(Guest guest)
    {
        List<Integer> cocktailIds = new ArrayList<>();
        for (Cocktail cocktail : guest.getCocktailList())
        {
            cocktailIds.add( cocktail.getId());
        }
        return cocktailDao.getCocktailsForGuest(cocktailIds);
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
