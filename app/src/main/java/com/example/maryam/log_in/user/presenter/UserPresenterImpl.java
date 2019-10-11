package com.example.maryam.log_in.user.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.maryam.log_in.api.UserApi;
import com.example.maryam.log_in.dto.User;
import com.example.maryam.log_in.resource.RealmInstanceGenerator;
import com.example.maryam.log_in.resource.RetrofitGenerator;
import com.example.maryam.log_in.user.OnFindAllUsersAndLoginUserListener;
import com.example.maryam.log_in.user.OnUserListener;
import com.example.maryam.log_in.user.model.UserModel;
import com.example.maryam.log_in.user.model.UserModelImpl;
import com.example.maryam.log_in.user.view.ProfileActivity;
import com.example.maryam.log_in.user.view.UserProfile;
import com.example.maryam.log_in.user.view.UserView;

import java.util.List;

import javax.annotation.Nonnull;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.maryam.log_in.item.view.ItemProfileActivity.onEdit;

/**
 * Created by maryam on 10/1/19.
 */

public class UserPresenterImpl implements UserPresenter {

    private UserModel userModel;
    private UserView userView;
    private UserProfile userProfile;

    public UserPresenterImpl(UserView userView) {
        this.userModel = new UserModelImpl();
        this.userView = userView;
    }

    public UserPresenterImpl(UserProfile userProfile) {
        this.userModel = new UserModelImpl();
        this.userProfile = userProfile;
    }

    @Override
    public void findAllUsers(final OnFindAllUsersAndLoginUserListener onFindAllUsersAndLoginUserListener) {
        userModel.findAll(new OnFindAllUsersAndLoginUserListener() {

            @Override
            public void onSuccess(@NonNull List<User> userList, @Nonnull String currentUser) {
                if (onFindAllUsersAndLoginUserListener != null){
                    onFindAllUsersAndLoginUserListener.onSuccess(userList, currentUser);
                }
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                if (onFindAllUsersAndLoginUserListener != null){
                    onFindAllUsersAndLoginUserListener.onError(throwable);
                }
            }

            @Override
            public void onError() {
                if (onFindAllUsersAndLoginUserListener != null){
                    onFindAllUsersAndLoginUserListener.onError();
                }
            }
        });
    }

    @Override
    public void updateUser(String firstName, String lastName, String username, String password, String id) {
        boolean userNameIsEmpty = TextUtils.isEmpty(username);
        boolean passwordIsEmpty = TextUtils.isEmpty(password);
        boolean firstNameIsEmpty = TextUtils.isEmpty(firstName);
        boolean lastNameIsEmpty = TextUtils.isEmpty(lastName);
        if (userNameIsEmpty || passwordIsEmpty || firstNameIsEmpty || lastNameIsEmpty) {
            if (firstNameIsEmpty) {
                userProfile.showErrorOnEmptyFirstNameField();
            }
            if (lastNameIsEmpty) {
                userProfile.showErrorOnEmptyLastNameField();
            }
            if (userNameIsEmpty) {
                userProfile.showErrorOnEmptyUsernameField();
            }
            if (passwordIsEmpty) {
                userProfile.showErrorOnEmptyPasswordField();
            }
        } else {
            userModel.update(firstName, lastName, username, password, id, new OnUserListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onSuccess(@Nonnull User user) {
                    userProfile.onSuccessfulUpdateUser();
                }

                @Override
                public void onSuccess(@NonNull List<User> userList) {

                }

                @Override
                public void onError(@NonNull Throwable throwable) {
                    userProfile.showOnError(throwable);
                }

                @Override
                public void onError() {
                    userProfile.showOnNotSuccessfulUpdateUser();
                }
            });
        }
    }

    @Override
    public void saveUser(String firstName, String lastName, String username, String password) {
        boolean userNameIsEmpty = TextUtils.isEmpty(username);
        boolean passwordIsEmpty = TextUtils.isEmpty(password);
        boolean firstNameIsEmpty = TextUtils.isEmpty(firstName);
        boolean lastNameIsEmpty = TextUtils.isEmpty(lastName);
        if (userNameIsEmpty || passwordIsEmpty || firstNameIsEmpty || lastNameIsEmpty) {
            if (firstNameIsEmpty) {
                userProfile.showErrorOnEmptyFirstNameField();
            }
            if (lastNameIsEmpty) {
                userProfile.showErrorOnEmptyLastNameField();
            }
            if (userNameIsEmpty) {
                userProfile.showErrorOnEmptyUsernameField();
            }
            if (passwordIsEmpty) {
                userProfile.showErrorOnEmptyPasswordField();
            }
        } else {
            userModel.create(firstName, lastName, username, password, new OnUserListener() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onSuccess(@Nonnull User user) {
                    userProfile.onSuccessfulSaveUser();
                }

                @Override
                public void onSuccess(@NonNull List<User> userList) {

                }

                @Override
                public void onError(@NonNull Throwable throwable) {
                    userProfile.showOnError(throwable);
                }

                @Override
                public void onError() {
                    userProfile.showOnNotSuccessfulSaveUser();
                }
            });
        }
    }

    @Override
    public void delete(User selectedUser) {
        userModel.delete(selectedUser, new OnUserListener() {
            @Override
            public void onSuccess() {
                userProfile.onDeleteAcitivity();
            }

            @Override
            public void onSuccess(@Nonnull User user) {

            }

            @Override
            public void onSuccess(@NonNull List<User> userList) {

            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                userProfile.showOnError(throwable);
            }

            @Override
            public void onError() {
                userProfile.showOnNotSuccessfulSaveUser();
            }
        });
    }

}
