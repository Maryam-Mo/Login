package com.example.maryam.log_in.item.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.maryam.log_in.R;
import com.example.maryam.log_in.item.view.itemmain.ItemMainActivity;
import com.example.maryam.log_in.user.view.ViewUserRecycler;
import com.example.maryam.log_in.user.view.ViewUsersActivity;

public class MainActivity extends AppCompatActivity {
    private Button user;
    private Button item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        onUserButtonClicked();

        onItemButtonClicked();
    }

    public void onItemButtonClicked() {
        item = findViewById(R.id.itemBtn);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ItemMainActivity.class);
                startActivityForResult(intent, RESULT_OK);
            }
        });
    }

    public void onUserButtonClicked() {
        user = findViewById(R.id.userBtn);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewUserRecycler.class);
                startActivity(intent);
            }
        });
    }


}
