package com.elkhamitech.projectkeeper.data.roomdatabase.crud;

import com.elkhamitech.projectkeeper.data.roomdatabase.PasswordsDatabase;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.UserModel;

import javax.inject.Inject;

public class UserCrud {

//    public static void populateAsync(@NonNull final PasswordsDatabase db) {
//        PopulateDbAsync task = new PopulateDbAsync(db);
//        task.execute();
//    }

    private final PasswordsDatabase db;

    @Inject
    public  UserCrud(PasswordsDatabase db){
        this.db = db;
    }

    public long createUser( UserModel user){

        return db.userDao().createUser(user);
    }

    public UserModel getUser( String pinCode){

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
