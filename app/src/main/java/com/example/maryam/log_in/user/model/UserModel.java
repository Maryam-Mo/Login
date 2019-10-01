package com.example.maryam.log_in.user.model;

import com.example.maryam.log_in.user.OnFindAllUsersListener;

/**
 * Created by maryam on 10/1/19.
 */

public interface UserModel {
    void findAll(OnFindAllUsersListener onFindAllUsersListener);
}
