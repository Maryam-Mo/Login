package com.example.maryam.log_in.login.model;

import android.support.annotation.NonNull;

import com.example.maryam.log_in.dto.LoginUser;
import com.example.maryam.log_in.dto.User;
import com.example.maryam.log_in.login.presenter.LoginPresenter;
import com.example.maryam.log_in.login.realm.RealmLo;
import com.example.maryam.log_in.login.webservice.OnValidateUserListener;
import com.example.maryam.log_in.login.webservice.RetrofitLogin;

/**
 * Created by maryam on 9/25/19.
 */

public class LoginModelImpl implements LoginModel {

    @Override
    public void login(String username, String password, final OnSuccessfulLoginListener onSuccessfulLoginListener) {

        RetrofitLogin.INSTANCE.getLoginWebService().validateUser(username, password, new OnValidateUserListener() {
            @Override
            public void onSuccess(@NonNull LoginUser user) {
                if (user != null) {
                    RealmLo.INSTANCE.getRealmLogin().create(user);
                    if (onSuccessfulLoginListener != null){
                        onSuccessfulLoginListener.clearFields();
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                if (onSuccessfulLoginListener != null) {
                    onSuccessfulLoginListener.onError(throwable);
                }
            }

            @Override
            public void onError() {
                if (onSuccessfulLoginListener != null) {
                    onSuccessfulLoginListener.onError();
                }
            }

        });;
    }
}
