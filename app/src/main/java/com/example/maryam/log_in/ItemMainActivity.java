package com.example.maryam.log_in;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ItemMainActivity extends AppCompatActivity {
    private Button create;
    private Button view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_main);
        ViewItemsActivity.selectedItem = null;
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        onCreateButtonClicked();
        onViewButtonClicked();
    }

    public void onCreateButtonClicked() {
        create = findViewById(R.id.createBtn);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemMainActivity.this, ItemProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onViewButtonClicked() {
        view = findViewById(R.id.viewBtn);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemMainActivity.this, ViewItemsActivity.class);
                startActivity(intent);
            }
        });
    }
}
