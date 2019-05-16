package com.elkhamitechnologies.projectkeeper.presenter;

import com.elkhamitechnologies.projectkeeper.Constants;
import com.elkhamitechnologies.projectkeeper.data.roomdatabase.LocalDbRepository;
import com.elkhamitechnologies.projectkeeper.data.roomdatabase.crud.UserCrud;
import com.elkhamitechnologies.projectkeeper.data.roomdatabase.model.UserModel;
import com.elkhamitechnologies.projectkeeper.data.sharedpreferences.CacheRepository;
import com.elkhamitechnologies.projectkeeper.ui.viewnotifiyers.SetViewNotifier;
import com.elkhamitechnologies.projectkeeper.ui.viewnotifiyers.WelcomeNotifier;
import com.elkhamitechnologies.projectkeeper.utils.accesshandler.Ciphering;

import javax.inject.Inject;

public class WelcomePresenter implements SetViewNotifier<WelcomeNotifier> {

    private CacheRepository cacheRepository;
    private LocalDbRepository<UserModel, String> userCrud;
    private WelcomeNotifier listener;

    @Inject
    BasePresenter basePresenter;

    @Inject
    WelcomePresenter(CacheRepository cacheRepository, UserCrud userCrud) {
        this.cacheRepository = cacheRepository;
        this.userCrud = userCrud;
    }

    @Override
    public void setListener(WelcomeNotifier listener) {
        this.listener = listener;
    }

    public void saveKeyboardType(boolean isNumericKeyboard) {
        basePresenter.saveKeyboardType(isNumericKeyboard);
    }

    public boolean getKeyboardStatus() {
        return basePresenter.getKeyboardStatus();
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
        UserModel userModel = new UserModel();
        userModel.setPin(Ciphering.encrypt(pinCode, Constants.ENCRYPT_KEY));

        long id = userCrud.create(userModel);
        cacheRepository.createLoginSession(id, pinCode);
        listener.onPasswordCreatedSuccessfully();
    }

}
