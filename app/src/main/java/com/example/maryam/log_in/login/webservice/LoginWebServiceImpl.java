package com.example.maryam.log_in.login.webservice;

import com.example.maryam.log_in.api.UserApi;
import com.example.maryam.log_in.dto.User;
import com.example.maryam.log_in.resource.RetrofitGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by maryam on 9/25/19.
 */

public class LoginWebServiceImpl implements LoginWebService{
    private User user;
    private OnValidateUserListener onValidateUserListener;


//    public void setOnValidateUserListener(OnValidateUserListener onValidateUserListener){
//        this.onValidateUserListener = onValidateUserListener;
//    }

    @Override
    public void validateUser(String username, String password, final OnValidateUserListener onValidateUserListener) {
        UserApi userApi = RetrofitGenerator.INSTANCE.generateRetrofit().create(UserApi.class);
        Call<User> call = userApi.validateUser(username, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    if (onValidateUserListener != null){
                        onValidateUserListener.onError();
                    }
                }
                if (onValidateUserListener != null){
                    onValidateUserListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (onValidateUserListener != null){
                    onValidateUserListener.onError(t);
                }
            }
        });
    }
}
