package com.example.maryam.log_in.user.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.maryam.log_in.R;
import com.example.maryam.log_in.dto.User;
import com.example.maryam.log_in.user.OnFindAllUsersAndLoginUserListener;
import com.example.maryam.log_in.user.presenter.UserPresenter;
import com.example.maryam.log_in.user.presenter.UserPresenterImpl;

import java.util.List;

import javax.annotation.Nonnull;


public class ViewUsersActivity extends AppCompatActivity implements UserView {
    private ListView userList;
    private String loginUser;
    private UserPresenter userPresenter;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);
        userList = findViewById(R.id.userList);
        userPresenter = new UserPresenterImpl(this);
        userPresenter.findAllUsers(new OnFindAllUsersAndLoginUserListener() {

            @Override
            public void onSuccess(@NonNull List<User> userList, @Nonnull String currentUser) {
                loginUser = currentUser;
                initializingUserList(userList);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                showOnNotSuccessfulFind(throwable.getMessage());
            }

            @Override
            public void onError() {
                showOnNotSuccessfulFind("There is an error! :D");
            }
        });
        backButtonClicked();
    }

    private void backButtonClicked() {
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initializingUserList(final List<User> users) {
        CustomListAdapter customListAdapter = new CustomListAdapter(ViewUsersActivity.this, users);
        userList = findViewById(R.id.userList);
        userList.setAdapter(customListAdapter);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                User selectedUser = users.get(position);
                if (loginUser.equalsIgnoreCase("admin") || selectedUser.getUsername().equalsIgnoreCase(loginUser)) {
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
    public void showOnNotSuccessfulFind(String message) {
        Toast.makeText(ViewUsersActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
