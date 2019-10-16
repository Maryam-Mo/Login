package com.example.maryam.log_in.login.view.presenter;

/**
 * Created by maryam on 9/25/19.
 */

public interface LoginPresenter {
    void loginCredential(String username, String password);
    void showNotSuccesfulLoginResponse(String message);
    void clearField();
    void actOnSuccessfulLogin(String message);
}