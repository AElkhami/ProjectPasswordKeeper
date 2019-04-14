package com.elkhamitech.projectkeeper.data.roomdatabase.crud;

import com.elkhamitech.projectkeeper.data.roomdatabase.PasswordsDatabase;
import com.elkhamitech.projectkeeper.data.roomdatabase.model.UserModel;

import java.util.List;

import javax.inject.Inject;

public class UserCrud implements LocalDbRepository<UserModel, String> {

    private UserModel user = new UserModel();

    private final PasswordsDatabase db;

    @Inject
    public  UserCrud(PasswordsDatabase db){
        this.db = db;
    }

//    public long createUser(String pinCode){
//
//        user.setPin(pinCode);
//        return db.userDao().createUser(user);
//    }
//
//    public UserModel getUser( String pinCode){
//
//        if (user != null) {
//            return db.userDao().getRegisteredUser(pinCode);
//        }else{
//            return null;
//        }
//
//    }



    @Override
    public long create(UserModel userModel) {
        user.setPin(userModel.getPin());
        return db.userDao().createUser(user);
    }

    @Override
    public List<UserModel> getListDb(String id) {
        return null;
    }

    @Override
    public UserModel readFromDb(String pinCode) {
        if (user != null) {
            return db.userDao().getRegisteredUser(pinCode);
        }else{
            return null;
        }
    }

    @Override
    public void update(UserModel entity) {

    }

    @Override
    public void delete(UserModel entity) {

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
