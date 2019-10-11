package com.example.maryam.log_in.login.realm;

import com.example.maryam.log_in.dto.LoginUser;
import com.example.maryam.log_in.dto.User;

/**
 * Created by maryam on 9/25/19.
 */

public interface RealmLogin {
    void create(LoginUser user);
    LoginUser findLoginUser();
}
