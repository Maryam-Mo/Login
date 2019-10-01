package com.example.maryam.log_in.user.view;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.maryam.log_in.R;
import com.example.maryam.log_in.item.presenter.ViewItemsActivity;
import com.example.maryam.log_in.api.ItemApi;
import com.example.maryam.log_in.dto.Item;
import com.example.maryam.log_in.repository.Repository;
import com.example.maryam.log_in.resource.RetrofitGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ItemProfileActivity extends AppCompatActivity {
    private Button save;
    private EditText name;
    private EditText price;
    private EditText quantity;
    public static boolean onEdit;
    private Item item;
    public static Item updatedItem;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_profile);
        onSaveButtonClicked();
        name = findViewById(R.id.nameTxt);
        price = findViewById(R.id.priceTxt);
        quantity = findViewById(R.id.quantityTxt);
        if (ViewItemsActivity.selectedItem != null){
            item = ViewItemsActivity.selectedItem;
            name.setText(item.getName());
            price.setText(String.valueOf(item.getPrice()));
            quantity.setText(String.valueOf(item.getQuantity()));
            onEdit = true;
        } else {
            clearFields();
            onEdit = false;
        }
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void clearFields() {
        name.setText("");
        price.setText("");
        quantity.setText("");
    }

    public void onSaveButtonClicked() {
        save = findViewById(R.id.saveBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean nameIsEmpty = name.getText().toString().length() == 0;
                boolean priceIsEmpty = price.getText().toString().length() == 0;
                boolean quantityIsEmpty = quantity.getText().toString().length() == 0;
                if (nameIsEmpty || priceIsEmpty || quantityIsEmpty) {
                    if (nameIsEmpty) {
                        name.setError("Name is required!");
                    }
                    if (priceIsEmpty) {
                        price.setError("Price is required!");
                    }
                    if (quantityIsEmpty) {
                        quantity.setError("Quantity is required!");
                    }
                } else {
                    Item itemToSave = new Item();
                    itemToSave.setName(name.getText().toString());
                    itemToSave.setPrice(Double.parseDouble(price.getText().toString()));
                    itemToSave.setQuantity(Integer.parseInt(quantity.getText().toString()));
                    final ItemApi itemApi = RetrofitGenerator.INSTANCE.generateRetrofit().create(ItemApi.class);
                    if (!onEdit) {
                        Call<Item> createCall = itemApi.createItem(itemToSave);
                        createCall.enqueue(new Callback<Item>() {
                            @Override
                            public void onResponse(Call<Item> call, Response<Item> response) {
                                if (!response.isSuccessful()) {
                                    return;
                                }
                                Item receivedItem = response.body();
                                Repository.INSTANCE.getItemRepository().create(receivedItem);
                                Toast.makeText(ItemProfileActivity.this, "New Item is created successfully!", Toast.LENGTH_SHORT).show();
                                clearFields();
                            }

                            @Override
                            public void onFailure(Call<Item> call, Throwable t) {
                            }
                        });
                    } else {
                        itemToSave.setId(item.getId());
                        Call<Item> updateCall = itemApi.updateItem(itemToSave);
                        updateCall.enqueue(new Callback<Item>() {
                            @Override
                            public void onResponse(Call<Item> call, Response<Item> response) {
                                if (!response.isSuccessful()) {
                                    return;
                                }
                                updatedItem = response.body();
                                Repository.INSTANCE.getItemRepository().create(updatedItem);
                                Toast.makeText(ItemProfileActivity.this, "Item is updated successfully!", Toast.LENGTH_SHORT).show();
                                clearFields();
                            }

                            @Override
                            public void onFailure(Call<Item> call, Throwable t) {
                            }
                        });
                    }
                }
            }
        });
    }
}
