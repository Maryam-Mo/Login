package com.example.maryam.log_in;

import com.example.maryam.log_in.dto.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by maryam on 9/9/19.
 */
public interface ItemApi {

    @POST("item/create")
    Call<Item> createItem(@Body Item item);

    @POST("item/update")
    Call<Item> updateItem(@Body Item item);

    @GET("item/findAll")
    Call<List<Item>> findAllItems();
}
