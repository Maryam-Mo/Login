package com.example.maryam.log_in;

import com.example.maryam.log_in.login.model.LoginModel;
import com.example.maryam.log_in.login.model.LoginModelImpl;

/**
 * Created by maryam on 10/14/19.
 */

public class ModelFactory {

    public static LoginModel getLoginModel(){
        return LoginModelImpl.INSTANCE;
    }
}
