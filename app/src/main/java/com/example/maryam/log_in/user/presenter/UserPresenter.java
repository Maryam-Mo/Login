package com.example.maryam.log_in.user.presenter;

import com.example.maryam.log_in.dto.User;
import com.example.maryam.log_in.user.OnFindAllUsersAndLoginUserListener;

/**
 * Created by maryam on 10/1/19.
 */

public interface UserPresenter {
    void findAllUsers(OnFindAllUsersAndLoginUserListener onFindAllUsersAndLoginUserListener);
    void updateUser(String s, String s1, String s2, String s3, String id);
    void saveUser(String s, String s1, String s2, String s3);
    void delete(User selectedUser);
}
