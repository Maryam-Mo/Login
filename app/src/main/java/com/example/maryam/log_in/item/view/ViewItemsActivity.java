package com.example.maryam.log_in.item.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.maryam.log_in.R;
import com.example.maryam.log_in.api.ItemApi;
import com.example.maryam.log_in.dto.Item;
import com.example.maryam.log_in.repository.Repository;
import com.example.maryam.log_in.resource.RetrofitGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewItemsActivity extends AppCompatActivity {
    private ListView itemList;
    public static Item selectedItem;

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
        initializingItemList(Repository.INSTANCE.getItemRepository().findAll());
//      loadListWithData();
        ItemProfileActivity.onEdit = false;
    }

    private void loadListWithData() {
        itemList = findViewById(R.id.itemList);
        List<Item> itemListFromRealm = Repository.INSTANCE.getItemRepository().findAll();;
        if (itemListFromRealm.isEmpty()) {
            final ItemApi itemApi = RetrofitGenerator.INSTANCE.generateRetrofit().create(ItemApi.class);
            Call<List<Item>> itemsCall = itemApi.findAllItems();
            itemsCall.enqueue(new Callback<List<Item>>() {
                @Override
                public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(ViewItemsActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<Item> items = response.body();
                    Repository.INSTANCE.getItemRepository().create(items);
                    initializingItemList(Repository.INSTANCE.getItemRepository().findAll());
                }

                @Override
                public void onFailure(Call<List<Item>> call, Throwable t) {
                    Toast.makeText(ViewItemsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            initializingItemList(itemListFromRealm);
        }
    }

    private void initializingItemList(final List<Item> itemsFromRealm) {
        CustomItemListAdapter customItemListAdapter = new CustomItemListAdapter(ViewItemsActivity.this, itemsFromRealm);
        itemList = findViewById(R.id.itemList);
        itemList.setAdapter(customItemListAdapter);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectedItem = itemsFromRealm.get(position);
                Intent intent = new Intent(ViewItemsActivity.this, ItemProfileActivity.class);
                startActivityForResult(intent, RESULT_OK);
            }
        });
    }

}
