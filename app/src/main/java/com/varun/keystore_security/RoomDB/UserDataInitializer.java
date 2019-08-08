package com.varun.keystore_security.RoomDB;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

public class UserDataInitializer {

    public static void populateAsync(@NonNull final AppDatabase db, @NonNull UserData userData) {
        PopulateDbAsync task = new PopulateDbAsync(db, userData);
        task.execute();
    }

    private static void addUser(final AppDatabase db, UserData userData) {
        db.userDao().insertAll(userData);
    }

    private static void populateWithTestData(AppDatabase db, UserData userData) {

        addUser(db, userData);
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;
        private final UserData userr;

        PopulateDbAsync(AppDatabase db, UserData userData) {
            mDb = db;
            userr = userData;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb,userr);
            return null;
        }

    }
}
