package com.example.maryam.log_in.login.webservice;

/**
 * Created by maryam on 9/25/19.
 */

public interface LoginWebService {
    void validateUser(String username, String password, OnValidateUserListener onValidateUserListener);
}
