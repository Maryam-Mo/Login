package com.example.maryam.log_in.user.webservice;

import com.example.maryam.log_in.api.UserApi;
import com.example.maryam.log_in.dto.User;
import com.example.maryam.log_in.resource.RetrofitGenerator;
import com.example.maryam.log_in.user.OnFindAllUsersListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by maryam on 10/1/19.
 */

class UserWebServiceImpl implements UserWebService {
    @Override
    public void findAllUsers(final OnFindAllUsersListener onFindAllUsersListener) {
        UserApi userApi = RetrofitGenerator.INSTANCE.generateRetrofit().create(UserApi.class);
        Call<List<User>> userscall = userApi.findAllUsers();
        userscall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()) {
                    if (onFindAllUsersListener != null){
                        onFindAllUsersListener.onError();
                    }
                }
                if (onFindAllUsersListener != null){
                    onFindAllUsersListener.onSuccess(response.body());
                }
//                Realm realm = null;
//                try {
//                    realm = RealmInstanceGenerator.INSTANCE.generateRealmInstance();
//                    realm.executeTransaction(new Realm.Transaction() {
//                        @Override
//                        public void execute(Realm realm) {
//                            realm.insertOrUpdate(userList);
//                        }
//                    });
//                } finally {
//                    if(realm != null) {
//                        realm.close();
//                    }
//                }
//                initializingUserList(getUsersFromRealm());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                if (onFindAllUsersListener != null){
                    onFindAllUsersListener.onError(t);
                }
            }
        });
    }
}
