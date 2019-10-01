package com.example.maryam.log_in.user.webservice;

/**
 * Created by maryam on 9/25/19.
 */

public enum RetrofitUser {
    INSTANCE;

    private UserWebService userWebService;

    public UserWebService getUserWebService() {
        if (userWebService == null){
            userWebService = new UserWebServiceImpl();
        }
        return userWebService;
    }
}
