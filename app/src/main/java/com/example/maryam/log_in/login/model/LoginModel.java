package com.example.maryam.log_in.login.model;

/**
 * Created by maryam on 9/25/19.
 */

public interface LoginModel {
    void login(String username, String password, OnSuccessfulLoginListener onSuccessfulLoginListener);
}
