package com.example.maryam.log_in.user.webservice;

import com.example.maryam.log_in.dto.User;
import com.example.maryam.log_in.user.OnUserListener;

/**
 * Created by maryam on 10/1/19.
 */

public interface UserWebService {

    void findAllUsers(OnUserListener onUserListener);
    void createUser(User user, OnUserListener onUserListener);

    void updateUser(User userToSave, OnUserListener onUserListener);

    void deleteUser(User userToDelete, OnUserListener onUserListener);
}
