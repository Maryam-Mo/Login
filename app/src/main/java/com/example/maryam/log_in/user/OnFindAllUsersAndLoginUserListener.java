package com.example.maryam.log_in.user;

import android.support.annotation.NonNull;

import com.example.maryam.log_in.dto.User;

import java.util.List;

import javax.annotation.Nonnull;

/**
 * Created by maryam on 10/2/19.
 */

public interface OnFindAllUsersAndLoginUserListener {
    void onSuccess(@NonNull List<User> userList, @Nonnull String currentUser);
    void onError(@NonNull Throwable throwable);
    void onError();
}
