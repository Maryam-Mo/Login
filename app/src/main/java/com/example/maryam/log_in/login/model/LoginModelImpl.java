package com.example.maryam.log_in.login.model;

import android.support.annotation.NonNull;

import com.example.maryam.log_in.dto.User;
import com.example.maryam.log_in.login.presenter.LoginPresenter;
import com.example.maryam.log_in.login.realm.RealmLo;
import com.example.maryam.log_in.login.webservice.OnValidateUserListener;
import com.example.maryam.log_in.login.webservice.RetrofitLogin;

/**
 * Created by maryam on 9/25/19.
 */

public class LoginModelImpl implements LoginModel {
   private LoginPresenter loginPresenter;
   private OnSuccessfulLoginListener onSuccessfulLoginListener;

   public void setOnSuccessfulLoginListener(OnSuccessfulLoginListener onSuccessfulLoginListener) {
       this.onSuccessfulLoginListener = onSuccessfulLoginListener;
   }


    @Override
    public void login(String username, String password, final OnSuccessfulLoginListener onSuccessfulLoginListener) {

        RetrofitLogin.INSTANCE.getLoginWebService().validateUser(username, password, new OnValidateUserListener() {
            @Override
            public void onSuccess(@NonNull User user) {
                if (user != null) {
                    RealmLo.INSTANCE.getRealmLogin().create(user);
                    if (onSuccessfulLoginListener != null){
                        onSuccessfulLoginListener.clearFields();
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                if (onSuccessfulLoginListener != null) {
                    onSuccessfulLoginListener.onError(throwable);
                }
            }

            @Override
            public void onError() {
                if (onSuccessfulLoginListener != null) {
                    onSuccessfulLoginListener.onError();
                }
            }

        });
//        RetrofitLogin.INSTANCE.getLoginWebService().se

//        UserApi userApi = RetrofitGenerator.INSTANCE.generateRetrofit().create(UserApi.class);
//        Call<User> call = userApi.validateUser(username, password);
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if (!response.isSuccessful()) {
//                    loginPresenter.showNotSuccesfulLoginResponse("Username or Password is invalid!");
//                    return;
//                }
//                User user = response.body();
//                final LoginUser loginUser = new LoginUser();
//                loginUser.setUsername(user.getUsername());
//                Realm realm = null;
//                try {
//                    realm = RealmInstanceGenerator.INSTANCE.generateRealmInstance();
//                    realm.executeTransaction(new Realm.Transaction() {
//                        @Override
//                        public void execute(Realm realm) {
//                            realm.insertOrUpdate(loginUser);
//                        }
//                    });
//                } finally {
//                    if(realm != null) {
//                        realm.close();
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//            }
//        });
    }
}
