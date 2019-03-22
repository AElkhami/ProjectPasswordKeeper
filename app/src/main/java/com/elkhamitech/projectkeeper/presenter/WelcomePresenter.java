package com.elkhamitech.projectkeeper.presenter;

import android.os.Environment;

import com.elkhamitech.Constants;
import com.elkhamitech.data.PasswordsDatabase;
import com.elkhamitech.data.crud.UserCrud;
import com.elkhamitech.data.model.UserModel;
import com.elkhamitech.projectkeeper.utils.AccessHandler.SessionManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class WelcomePresenter {

    private WelcomePresenterListener listener;

    //for encryption and decryption
    private String normalTextEnc;
    private String normalTextDec;

    public WelcomePresenter(WelcomePresenterListener listener) {
        this.listener = listener;
    }

    public void validateInputs(String pinCode) {

        int charCount = pinCode.length();

        if (pinCode.equals("")) {
            listener.userMessage(Constants.ERROR_EMPTY_TEXT);
        } else if (charCount < 6) {
            listener.userMessage(Constants.ERROR_PASSWORD_LENGTH);
        } else {
            listener.onInputValidationSuccess(pinCode);
        }

    }

    public void createPassword(String pinCode) {

        long id = saveUserPassword( pinCode);
        saveUserSession(id);
        listener.onPasswordCreatedSuccessfully();
    }

    public long saveUserPassword(String pinCode) {

        UserModel user = new UserModel();

//            try {
//                normalTextEnc = AESHelper.encrypt(Constants.SEED, pinCode);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        user.setPin(pinCode);

        UserCrud.createUser(PasswordsDatabase.getDatabase(), user);

        return user.getRow_id();

    }

    public void saveUserSession(long id){

        SessionManager.createLoginSession(id, normalTextEnc);

    }

    //importing database
    public void importDB(String packageName) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + packageName
                        + "//databases//" + "PassKeeper";
                String backupDBPath = "/Password Wallet/PassKeeper";
                File backupDB = new File(data, currentDBPath);
                File currentDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                listener.userMessage(Constants.BACKUP_RESTORED);

            }
        } catch (Exception e) {

            listener.userMessage(e.toString());

        }
    }

}
