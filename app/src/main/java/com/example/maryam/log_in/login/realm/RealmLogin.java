package com.example.maryam.log_in.login.realm;

import com.example.maryam.log_in.dto.LoginUser;
import com.example.maryam.log_in.dto.User;

import java.util.List;

/**
 * Created by maryam on 9/25/19.
 */

public interface RealmLogin {
    void create(User user);
    List<LoginUser> findAll();
}
