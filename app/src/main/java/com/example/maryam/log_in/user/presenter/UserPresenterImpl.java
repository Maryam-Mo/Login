package com.example.maryam.log_in.user.presenter;

import android.support.annotation.NonNull;

import com.example.maryam.log_in.dto.User;
import com.example.maryam.log_in.user.OnFindAllUsersListener;
import com.example.maryam.log_in.user.model.UserModel;
import com.example.maryam.log_in.user.model.UserModelImpl;
import com.example.maryam.log_in.user.view.UserView;

import java.util.List;

/**
 * Created by maryam on 10/1/19.
 */

public class UserPresenterImpl implements UserPresenter {

    private UserModel userModel;
    private UserView userView;

    public UserPresenterImpl(UserView userView) {
        this.userModel = new UserModelImpl();
        this.userView = userView;
    }

    @Override
    public void findAllUsers(final OnFindAllUsersListener onFindAllUsersListener) {
        userModel.findAll(new OnFindAllUsersListener() {
            @Override
            public void onSuccess(@NonNull List<User> userList) {
                if (onFindAllUsersListener != null){
                    onFindAllUsersListener.onSuccess(userList);
                }
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                if (onFindAllUsersListener != null){
                    onFindAllUsersListener.onError(throwable);
                }
            }

            @Override
            public void onError() {
                if (onFindAllUsersListener != null){
                    onFindAllUsersListener.onError();
                }
            }
        });
    }
}
