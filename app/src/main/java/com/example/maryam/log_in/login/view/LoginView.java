package com.example.maryam.log_in.login.view;

/**
 * Created by maryam on 9/25/19.
 */

public interface LoginView {

    void showErrorOnUsername(String message);
    void showErrorOnPassword(String message);
    void actOnSuccessfulLogin(String message);
    void showOnNotSuccessfulLogin(String message);
    void clearFields();
}
