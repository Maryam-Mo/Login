package com.example.maryam.log_in.login.model;

import android.support.annotation.NonNull;

/**
 * Created by maryam on 9/30/19.
 */

public interface OnSuccessfulLoginListener {
    void clearFields();
    void onError(@NonNull Throwable throwable);
    void onError();

}
