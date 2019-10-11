package com.example.maryam.log_in.user.view;

/**
 * Created by maryam on 10/10/19.
 */

public interface UserProfile {
    void showErrorOnEmptyFirstNameField();
    void showErrorOnEmptyLastNameField();
    void showErrorOnEmptyUsernameField();
    void showErrorOnEmptyPasswordField();
    void onSuccessfulSaveUser();
    void onSuccessfulUpdateUser();
    void showOnNotSuccessfulSaveUser();
    void showOnError(Throwable throwable);
    void showOnNotSuccessfulUpdateUser();

    void onDeleteAcitivity();
}
