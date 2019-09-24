package com.example.maryam.log_in;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.maryam.log_in.dto.LoginUser;
import com.example.maryam.log_in.dto.User;
import com.example.maryam.log_in.resource.RealmInstanceGenerator;
import com.example.maryam.log_in.resource.RetrofitGenerator;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewUsersActivity extends AppCompatActivity {
    private ListView userList;
    private LoginUser loginUser;
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
        setContentView(R.layout.activity_view_users);
        userList = findViewById(R.id.userList);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Realm realm = Realm.getDefaultInstance();
        loginUser = realm.where(LoginUser.class).findFirst();
        List<User> userList = getUsersFromRealm();
        if (userList.size() <= 0) {
            UserApi userApi = getRetrofitGenerator().generateRetrofit().create(UserApi.class);
            Call<List<User>> userscall = userApi.findAllUsers();
            userscall.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(ViewUsersActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    final List<User> userList = response.body();
                    Realm realm = getRealmInstanceGenerator().generateRealmInstance();
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(userList);
                    realm.commitTransaction();
                    realm.close();
                    initializingUserList(getUsersFromRealm());
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    Toast.makeText(ViewUsersActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            initializingUserList(userList);
        }
    }

    private List<User> getUsersFromRealm() {
        Realm userRealm = Realm.getDefaultInstance();
        return userRealm.where(User.class).findAll();
    }

    private void initializingUserList(final List<User> users) {
        CustomListAdapter customListAdapter = new CustomListAdapter(ViewUsersActivity.this, users);
        userList = findViewById(R.id.userList);
        userList.setAdapter(customListAdapter);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                User selectedUser = users.get(position);
                if (loginUser.getUsername().equalsIgnoreCase("admin") || selectedUser.getUsername().equalsIgnoreCase(loginUser.getUsername())) {
                    Intent intent = new Intent(ViewUsersActivity.this, ProfileActivity.class)
                            .putExtra("object", selectedUser);
                    startActivity(intent);
                } else {
                    Toast.makeText(ViewUsersActivity.this, "Access Denied!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
