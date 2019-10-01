package com.example.maryam.log_in.user.webservice;

import com.example.maryam.log_in.user.OnFindAllUsersListener;

/**
 * Created by maryam on 10/1/19.
 */

public interface UserWebService {

    void findAllUsers(OnFindAllUsersListener onFindAllUsersListener);
}
