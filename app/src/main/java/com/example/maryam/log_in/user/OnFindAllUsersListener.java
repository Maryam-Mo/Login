package com.example.maryam.log_in.user;

import android.support.annotation.NonNull;

import com.example.maryam.log_in.dto.User;

import java.util.List;

/**
 * Created by maryam on 10/1/19.
 */

public interface OnFindAllUsersListener {
    void onSuccess(@NonNull List<User> userList);
    void onError(@NonNull Throwable throwable);
    void onError();
}
