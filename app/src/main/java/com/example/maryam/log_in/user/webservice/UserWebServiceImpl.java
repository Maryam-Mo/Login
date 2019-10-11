package com.example.maryam.log_in.user.webservice;

import com.example.maryam.log_in.api.UserApi;
import com.example.maryam.log_in.dto.User;
import com.example.maryam.log_in.resource.RetrofitGenerator;
import com.example.maryam.log_in.user.OnUserListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by maryam on 10/1/19.
 */

class UserWebServiceImpl implements UserWebService {

    @Override
    public void findAllUsers(final OnUserListener onUserListener) {
        UserApi userApi = RetrofitGenerator.INSTANCE.generateRetrofit().create(UserApi.class);
        Call<List<User>> userscall = userApi.findAllUsers();
        userscall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()) {
                    if (onUserListener != null){
                        onUserListener.onError();
                    }
                }
                if (onUserListener != null){
                    onUserListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                if (onUserListener != null){
                    onUserListener.onError(t);
                }
            }
        });
    }

    @Override
    public void createUser(final User userToSave, final OnUserListener onUserListener) {
        UserApi userApi = RetrofitGenerator.INSTANCE.generateRetrofit().create(UserApi.class);
        Call<User> createCall = userApi.createUser(userToSave);
        createCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    if (onUserListener != null){
                        onUserListener.onError();
                    }
                }
                if (onUserListener != null){
                    onUserListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (onUserListener != null){
                    onUserListener.onError(t);
                }
            }
        });
    }

    @Override
    public void updateUser(final User userToSave, final OnUserListener onUserListener) {
        UserApi userApi = RetrofitGenerator.INSTANCE.generateRetrofit().create(UserApi.class);
        Call<User> updateCall = userApi.updateUser(userToSave);
        updateCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    if (onUserListener != null){
                        onUserListener.onError();
                    }
                }
                if (onUserListener != null){
                    onUserListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (onUserListener != null){
                    onUserListener.onError(t);
                }
            }
        });
    }

    @Override
    public void deleteUser(final User userToDelete, final OnUserListener onUserListener) {
        UserApi userApi = RetrofitGenerator.INSTANCE.generateRetrofit().create(UserApi.class);
        Call<Void> deleteCall = userApi.delete(userToDelete.getId());
        deleteCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    if (onUserListener != null){
                        onUserListener.onError();
                    }
                }
                if (onUserListener != null){
                    onUserListener.onSuccess();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (onUserListener != null){
                    onUserListener.onError(t);
                }
            }
        });
    }
}
