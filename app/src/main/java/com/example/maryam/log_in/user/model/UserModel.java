package com.example.maryam.log_in.user.model;

import com.example.maryam.log_in.dto.User;
import com.example.maryam.log_in.user.OnFindAllUsersAndLoginUserListener;
import com.example.maryam.log_in.user.OnUserListener;

/**
 * Created by maryam on 10/1/19.
 */

public interface UserModel {
    void findAll(OnFindAllUsersAndLoginUserListener onFindAllUsersAndLoginUserListener);
    void create(String firstName, String lastName, String username, String password, OnUserListener onUserListener);
    void update(String firstName, String lastName, String username, String password, String id, OnUserListener onUserListener);

    void delete(User user, OnUserListener onUserListener);
}
