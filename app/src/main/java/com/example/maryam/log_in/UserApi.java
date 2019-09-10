package com.example.maryam.log_in;

import com.example.maryam.log_in.dto.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by maryam on 9/2/19.
 */

public interface UserApi {

    @GET("validate")
    Call<User> validateUser(@Query("username") String username, @Query("password") String password);

    @POST("create")
    Call<User> createUser(@Body User user);

    @POST("update")
    Call<User> updateUser(@Body User user);

    @GET("findAll")
    Call<List<User>> findAllUsers();

    @DELETE("delete")
    Call<Void> delete(@Query("id") String id);
}
