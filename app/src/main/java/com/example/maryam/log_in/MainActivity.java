package com.example.maryam.log_in;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.maryam.log_in.dto.User;

public class MainActivity extends AppCompatActivity {
    private Button user;
    private Button item;
    private User loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if (intent.hasExtra("object")){
            loginUser = intent.getParcelableExtra("object");
        }
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
                Intent intent = new Intent(MainActivity.this, ViewUsersActivity.class)
                        .putExtra("object", loginUser);
                startActivityForResult(intent, RESULT_OK);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK && resultCode == RESULT_OK) {
            user = data.getParcelableExtra("object");
        }
    }
}
