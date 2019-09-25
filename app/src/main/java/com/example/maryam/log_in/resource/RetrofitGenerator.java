package com.example.maryam.log_in.resource;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by maryam on 9/13/19.
 */

public enum RetrofitGenerator {
    INSTANCE;

    private String apiBaseUrl = "http://172.16.1.27:8070/api/";

    public Retrofit generateRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(apiBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
