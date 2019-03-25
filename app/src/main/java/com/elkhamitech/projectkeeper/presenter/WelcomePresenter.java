package com.elkhamitech.projectkeeper.presenter;

import android.os.Environment;

import com.elkhamitech.projectkeeper.Constants;
import com.elkhamitech.projectkeeper.data.roomdatabase.crud.UserCrud;
import com.elkhamitech.projectkeeper.data.sharedpreferences.Repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import javax.inject.Inject;

public class WelcomePresenter extends BasePresenter<WelcomePresenterListener> {

    private Repository repository;
    private UserCrud userCrud;

    @Inject
    public WelcomePresenter(Repository repository, UserCrud userCrud) {
        super(repository, userCrud);
        this.repository = repository;
        this.userCrud = userCrud;
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

    public void createNewUser(String pinCode) {
//        try {
//            normalTextEnc = AESHelper.encrypt(Constants.SEED, pinCode);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        long id = userCrud.createUser(pinCode);
        repository.createLoginSession(id, pinCode);
        listener.onPasswordCreatedSuccessfully();
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
