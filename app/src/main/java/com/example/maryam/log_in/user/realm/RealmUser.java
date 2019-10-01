package com.example.maryam.log_in.user.realm;

import com.example.maryam.log_in.dto.User;

import java.util.List;

/**
 * Created by maryam on 10/1/19.
 */

public interface RealmUser {
    List<User> findALl();

    void create(List<User> users);
}
