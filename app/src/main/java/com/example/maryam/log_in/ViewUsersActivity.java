package com.example.maryam.log_in;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.maryam.log_in.dto.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewUsersActivity extends AppCompatActivity {
    private ListView userList;
    private User user;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);
        userList = findViewById(R.id.userList);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
        if (intent.hasExtra("object")) {
            user = intent.getParcelableExtra("object");
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.16.1.27:8070/api/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserApi userApi = retrofit.create(UserApi.class);
        Call<List<User>> userscall = userApi.findAllUsers();
        userscall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(ViewUsersActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                final List<User> users = response.body();
                CustomListAdapter customListAdapter = new CustomListAdapter(ViewUsersActivity.this, users);
                userList = findViewById(R.id.userList);
                userList.setAdapter(customListAdapter);
                    userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        User selectedUser = users.get(position);
                        if (user.getUsername().equalsIgnoreCase("admin") || selectedUser.getUsername().equalsIgnoreCase(user.getUsername())) {
                            Intent intent = new Intent(ViewUsersActivity.this, ProfileActivity.class)
                                    .putExtra("object", selectedUser);
                            startActivityForResult(intent, RESULT_OK);
                        } else {
                            Toast.makeText(ViewUsersActivity.this, "Access Denied!", Toast.LENGTH_LONG).show();
                        }
                        }
                    });
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(ViewUsersActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        if (user != null) {
            Intent intent = new Intent();
            intent.putExtra("object", user);
            setResult(RESULT_OK, intent);
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (user != null) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    onBackPressed();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
        return true;
    }
}
