package com.example.maryam.log_in.user.model;

import android.support.annotation.NonNull;

import com.example.maryam.log_in.dto.User;
import com.example.maryam.log_in.user.realm.RealmUs;
import com.example.maryam.log_in.user.OnFindAllUsersListener;
import com.example.maryam.log_in.user.webservice.RetrofitUser;

import java.util.List;

/**
 * Created by maryam on 10/1/19.
 */

public class UserModelImpl implements UserModel{

    @Override
    public void findAll(final OnFindAllUsersListener onFindAllUsersListener) {
        List<User> userList = RealmUs.INSTANCE.getRealmUser().findALl();
        if (userList.size() <= 0) {
            RetrofitUser.INSTANCE.getUserWebService().findAllUsers(new OnFindAllUsersListener() {
                @Override
                public void onSuccess(@NonNull List<User> userList) {
                    if (userList != null) {
                        RealmUs.INSTANCE.getRealmUser().create(userList);
                        List<User> users = RealmUs.INSTANCE.getRealmUser().findALl();
                        if (onFindAllUsersListener != null) {
                            onFindAllUsersListener.onSuccess(users);
                        }
                    }

                }

                @Override
                public void onError(@NonNull Throwable throwable) {
                    if (onFindAllUsersListener != null) {
                        onFindAllUsersListener.onError(throwable);
                    }
                }

                @Override
                public void onError() {
                    if (onFindAllUsersListener != null) {
                        onFindAllUsersListener.onError();
                    }
                }
            });
        } else {
            if (onFindAllUsersListener != null) {
                onFindAllUsersListener.onSuccess(userList);
            }
        }
    }
}
