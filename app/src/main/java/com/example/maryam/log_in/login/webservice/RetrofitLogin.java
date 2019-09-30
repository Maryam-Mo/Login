package com.example.maryam.log_in.login.webservice;

import com.example.maryam.log_in.login.realm.RealmLogin;
import com.example.maryam.log_in.login.realm.RealmLoginImpl;
import com.example.maryam.log_in.resource.RealmInstanceGenerator;

/**
 * Created by maryam on 9/25/19.
 */

public enum RetrofitLogin {
    INSTANCE;

    private LoginWebService loginWebService;

    public LoginWebService getLoginWebService() {
        if (loginWebService == null){
            loginWebService = new LoginWebServiceImpl();
        }
        return loginWebService;
    }
}
