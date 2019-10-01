package com.example.maryam.log_in.user.presenter;

import com.example.maryam.log_in.user.OnFindAllUsersListener;

/**
 * Created by maryam on 10/1/19.
 */

public interface UserPresenter {
    void findAllUsers(OnFindAllUsersListener onFindAllUsersListener);
}
