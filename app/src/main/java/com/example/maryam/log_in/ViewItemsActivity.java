package com.example.maryam.log_in;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.maryam.log_in.dto.Item;
import com.example.maryam.log_in.resource.RetrofitGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewItemsActivity extends AppCompatActivity {
    private ListView itemList;
    private Item item;
    public static Item selectedItem;
    private List<Item> items;
    private RetrofitGenerator retrofitGenerator;

    public RetrofitGenerator getRetrofitGenerator() {
        if (retrofitGenerator == null){
            retrofitGenerator = new RetrofitGenerator();
        }
        return retrofitGenerator;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        loadListWithData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ItemProfileActivity.onEdit){
            for (Item item: items){
                if (selectedItem.getName().equalsIgnoreCase(item.getName()) && ItemProfileActivity.updatedItem != null){
                    int index = items.indexOf(item);
                    items.set(index, ItemProfileActivity.updatedItem);
                }
            }
            CustomItemListAdapter customItemListAdapter = new CustomItemListAdapter(ViewItemsActivity.this, items);
            itemList = findViewById(R.id.itemList);
            itemList.setAdapter(customItemListAdapter);
//            loadListWithData();
            ItemProfileActivity.onEdit = false;
        }
    }

    private void loadListWithData() {
        itemList = findViewById(R.id.itemList);
        final ItemApi itemApi = getRetrofitGenerator().generateRetrofit().create(ItemApi.class);
        Call<List<Item>> itemsCall = itemApi.findAllItems();
        itemsCall.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(ViewItemsActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                items = response.body();
                CustomItemListAdapter customItemListAdapter = new CustomItemListAdapter(ViewItemsActivity.this, items);
                itemList = findViewById(R.id.itemList);
                itemList.setAdapter(customItemListAdapter);
                itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        selectedItem = items.get(position);
                        Intent intent = new Intent(ViewItemsActivity.this, ItemProfileActivity.class);
                        startActivityForResult(intent, RESULT_OK);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(ViewItemsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
