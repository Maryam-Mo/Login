package com.example.maryam.log_in.login.webservice;

import android.support.annotation.NonNull;

import com.example.maryam.log_in.dto.User;

import javax.annotation.Nonnull;

/**
 * Created by maryam on 9/30/19.
 */

public interface OnValidateUserListener {
    void onSuccess(@NonNull User user);
    void onError(@NonNull Throwable throwable);
    void onError();
}
