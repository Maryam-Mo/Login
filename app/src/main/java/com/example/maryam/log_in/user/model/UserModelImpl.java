package com.example.maryam.log_in.user.model;

import android.support.annotation.NonNull;

import com.example.maryam.log_in.dto.User;
import com.example.maryam.log_in.login.realm.RealmLo;
import com.example.maryam.log_in.user.OnFindAllUsersAndLoginUserListener;
import com.example.maryam.log_in.user.realm.RealmUs;
import com.example.maryam.log_in.user.OnUserListener;
import com.example.maryam.log_in.user.webservice.RetrofitUser;

import java.util.List;

import javax.annotation.Nonnull;

/**
 * Created by maryam on 10/1/19.
 */

public class UserModelImpl implements UserModel{

    private String currentLoginUser;

    @Override
    public void findAll(final OnFindAllUsersAndLoginUserListener onFindAllUsersAndLoginUserListener) {
        List<User> userList = RealmUs.INSTANCE.getRealmUser().findALl();
        currentLoginUser = RealmLo.INSTANCE.getRealmLogin().findLoginUser().getUsername();
        if (userList.size() <= 0) {
            RetrofitUser.INSTANCE.getUserWebService().findAllUsers(new OnUserListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onSuccess(@Nonnull User user) {

                }

                @Override
                public void onSuccess(@NonNull List<User> userList) {
                    if (userList != null) {
                        RealmUs.INSTANCE.getRealmUser().create(userList);
                        List<User> users = RealmUs.INSTANCE.getRealmUser().findALl();
                        if (onFindAllUsersAndLoginUserListener != null) {
                            onFindAllUsersAndLoginUserListener.onSuccess(users, currentLoginUser);
                        }
                    }

                }

                @Override
                public void onError(@NonNull Throwable throwable) {
                    if (onFindAllUsersAndLoginUserListener != null) {
                        onFindAllUsersAndLoginUserListener.onError(throwable);
                    }
                }

                @Override
                public void onError() {
                    if (onFindAllUsersAndLoginUserListener != null) {
                        onFindAllUsersAndLoginUserListener.onError();
                    }
                }
            });
        } else {
            if (onFindAllUsersAndLoginUserListener != null) {
                onFindAllUsersAndLoginUserListener.onSuccess(userList, currentLoginUser);
            }
        }
    }

    @Override
    public void create(String firstName, String lastName, String username, String password, final OnUserListener onUserListener) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(password);
        RetrofitUser.INSTANCE.getUserWebService().createUser(user, new OnUserListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onSuccess(@Nonnull User user) {
                RealmUs.INSTANCE.getRealmUser().create(user);
                if (onUserListener != null){
                    onUserListener.onSuccess(user);
                }
            }

            @Override
            public void onSuccess(@NonNull List<User> userList) {

            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                if (onUserListener != null){
                    onUserListener.onError(throwable);
                }
            }

            @Override
            public void onError() {
                if (onUserListener != null){
                    onUserListener.onError();
                }
            }
        });
    }

    @Override
    public void update(String firstName, String lastName, String username, String password, String id, final OnUserListener onUserListener) {
        User user = new User(id, username, lastName, firstName, password);
        RetrofitUser.INSTANCE.getUserWebService().updateUser(user, new OnUserListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onSuccess(@Nonnull User user) {
                RealmUs.INSTANCE.getRealmUser().create(user);
                if (onUserListener != null){
                    onUserListener.onSuccess(user);
                }
            }

            @Override
            public void onSuccess(@NonNull List<User> userList) {

            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                if (onUserListener != null){
                    onUserListener.onError(throwable);
                }
            }

            @Override
            public void onError() {
                if (onUserListener != null){
                    onUserListener.onError();
                }
            }
        });
    }

    @Override
    public void delete(final User user, final OnUserListener onUserListener) {
        RetrofitUser.INSTANCE.getUserWebService().deleteUser(user, new OnUserListener() {
            @Override
            public void onSuccess() {
                RealmUs.INSTANCE.getRealmUser().delete(user);
                if (onUserListener != null){
                    onUserListener.onSuccess();
                }
            }

            @Override
            public void onSuccess(@Nonnull User user) {

            }

            @Override
            public void onSuccess(@NonNull List<User> userList) {

            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                if (onUserListener != null){
                    onUserListener.onError(throwable);
                }
            }

            @Override
            public void onError() {
                if (onUserListener != null){
                    onUserListener.onError();
                }
            }
        });
    }

}
