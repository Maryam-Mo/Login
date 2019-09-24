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
import com.example.maryam.log_in.dto.User;
import com.example.maryam.log_in.resource.RealmInstanceGenerator;
import com.example.maryam.log_in.resource.RetrofitGenerator;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewItemsActivity extends AppCompatActivity {
    private ListView itemList;
    private Item item;
    public static Item selectedItem;
    private List<Item> items;
    private RetrofitGenerator retrofitGenerator;
    private RealmInstanceGenerator realmInstanceGenerator;

    public RealmInstanceGenerator getRealmInstanceGenerator() {
        if (realmInstanceGenerator == null){
            realmInstanceGenerator = new RealmInstanceGenerator();
        }
        return realmInstanceGenerator;
    }

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
        List<Item> itemListFromRealm = getItemsFromRealm();
        if (itemListFromRealm.size() <= 0) {
            final ItemApi itemApi = getRetrofitGenerator().generateRetrofit().create(ItemApi.class);
            Call<List<Item>> itemsCall = itemApi.findAllItems();
            itemsCall.enqueue(new Callback<List<Item>>() {
                @Override
                public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(ViewItemsActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    items = response.body();
                    Realm realm = getRealmInstanceGenerator().generateRealmInstance();
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(items);
                    realm.commitTransaction();
                    realm.close();
                    initializingItemList(getItemsFromRealm());
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

    private void initializingItemList(List<Item> itemsFromRealm) {
        CustomItemListAdapter customItemListAdapter = new CustomItemListAdapter(ViewItemsActivity.this, itemsFromRealm);
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

    private List<Item> getItemsFromRealm() {
        Realm itemRealm = Realm.getDefaultInstance();
        return itemRealm.where(Item.class).findAll();
    }
}
