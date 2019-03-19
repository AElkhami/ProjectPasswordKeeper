package com.elkhamitech.data.crud;

import com.elkhamitech.data.PasswordsDatabase;
import com.elkhamitech.data.model.UserModel;

public class UserCrud {

//    public static void populateAsync(@NonNull final PasswordsDatabase db) {
//        PopulateDbAsync task = new PopulateDbAsync(db);
//        task.execute();
//    }

    public static long createUser(PasswordsDatabase db, UserModel user){

        return db.userDao().createUser(user);
    }

    public static UserModel getUser(PasswordsDatabase db, String pinCode){

        return db.userDao().getRegisteredUser(pinCode);
    }


//    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
//
//        private final PasswordsDatabase mDb;
//
//        PopulateDbAsync(PasswordsDatabase db) {
//            mDb = db;
//        }
//
//        @Override
//        protected Void doInBackground(final Void... params) {
//
//            return null;
//        }
//
//    }
}
