package bb.incognito.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import bb.incognito.AppDatabase;
import bb.incognito.dao.GuestDao;
import bb.incognito.model.Guest;
import bb.incognito.model.GuestWithCocktails;

public class GuestRepository {
    private GuestDao guestDao;
    private LiveData<List<Guest>> allGuests;

    public GuestRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        guestDao = db.guestDao();
        allGuests = guestDao.allGuests();
    }

    public LiveData<Guest> getGuest(Integer id) { return guestDao.getGuest(id); }
    public LiveData<List<Guest>> getAllGuests() {
        return allGuests;
    }

    public void update(Guest guest){
        new updateAsyncTask(guestDao).execute(guest);
    }

    public void insert(Guest guest) {
        new insertAsyncTask(guestDao).execute(guest);
    }

    public void delete(Guest guest) {
        new deleteAsyncTask(guestDao).execute(guest);
    }

    private static class insertAsyncTask extends AsyncTask<Guest, Void, Void> {
        private GuestDao guestDao;

        insertAsyncTask(GuestDao guestDao) {
            this.guestDao = guestDao;
        }

        @Override
        protected Void doInBackground(final Guest... params) {
            guestDao.insertGuest(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Guest, Void, Void> {
        private GuestDao guestDao;

        updateAsyncTask(GuestDao guestDao) {
            this.guestDao = guestDao;
        }

        @Override
        protected Void doInBackground(final Guest... params) {
            guestDao.update(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Guest, Void, Void> {
        private GuestDao guestDao;

        deleteAsyncTask(GuestDao guestDao) {
            this.guestDao = guestDao;
        }

        @Override
        protected Void doInBackground(final Guest... params) {
            guestDao.delete(params[0]);
            return null;
        }
    }
}