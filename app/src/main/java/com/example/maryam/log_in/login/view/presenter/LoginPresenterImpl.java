package com.example.maryam.log_in.login.view.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.maryam.log_in.ModelFactory;
import com.example.maryam.log_in.login.model.OnSuccessfulLoginListener;
import com.example.maryam.log_in.login.view.LoginView;

/**
 * Created by maryam on 9/25/19.
 */

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView loginView;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
    }

    public LoginPresenterImpl(){}

    @Override
    public void loginCredential(String username, String password) {
        boolean usernameIsEmpty = TextUtils.isEmpty(username);
        boolean passwordIsEmpty = TextUtils.isEmpty(password);
        if (usernameIsEmpty || passwordIsEmpty) {
            if (usernameIsEmpty) {
                loginView.showErrorOnUsername(username);
            }
            if (passwordIsEmpty) {
                loginView.showErrorOnPassword(password);
            }
        } else {
            ModelFactory.getLoginModel().login(username, password, new OnSuccessfulLoginListener() {
                @Override
                public void clearFields() {
                    clearField();
                }

                @Override
                public void onError(@NonNull Throwable throwable) {
                    showNotSuccesfulLoginResponse(throwable.getMessage());
                }

                @Override
                public void onError() {
                    showNotSuccesfulLoginResponse("Username or Password is wrong!");
                }
            });
        }
    }

    @Override
    public void showNotSuccesfulLoginResponse(String message) {
        loginView.showOnNotSuccessfulLogin(message);
    }

    @Override
    public void clearField() {
        loginView.clearFields();
    }

    @Override
    public void actOnSuccessfulLogin(String message) {
        loginView.actOnSuccessfulLogin(message);
    }
}
