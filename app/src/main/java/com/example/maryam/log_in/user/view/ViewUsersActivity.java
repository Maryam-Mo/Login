package com.example.maryam.log_in.user.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maryam.log_in.R;
import com.example.maryam.log_in.dto.User;
import com.example.maryam.log_in.user.OnFindAllUsersAndLoginUserListener;
import com.example.maryam.log_in.user.presenter.UserPresenter;
import com.example.maryam.log_in.user.presenter.UserPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;


public class ViewUsersActivity extends AppCompatActivity implements UserView {
    private ListView userList;
    private EditText filter;
    private String loginUser;
    private Button btn_prev;
    private Button btn_next;
    private TextView title;
    private UserPresenter userPresenter;
    private CustomListAdapter customListAdapter;
    private List<User> userLists;
    private int TOTAL_LIST_ITEMS;
    private int NUM_ITEMS_PAGE = 2;
    private int pageCount ;
    private int increment = 0;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);
        userList = findViewById(R.id.userList);
        filter = findViewById(R.id.searchFilter);
        btn_prev = findViewById(R.id.prev);
        btn_next = findViewById(R.id.next);
        btn_prev.setEnabled(false);
        title    = findViewById(R.id.title);
        userPresenter = new UserPresenterImpl(this);
        userPresenter.findAllUsers(new OnFindAllUsersAndLoginUserListener() {

            @Override
            public void onSuccess(@NonNull List<User> userList, @Nonnull String currentUser) {
                loginUser = currentUser;
                userLists = userList;
                TOTAL_LIST_ITEMS = userList.size();
                int val = TOTAL_LIST_ITEMS%NUM_ITEMS_PAGE;
                val = val==0?0:1;
                pageCount = TOTAL_LIST_ITEMS/NUM_ITEMS_PAGE+val;
                loadList(0);
                CheckEnable();

//                initializingUserList(userLists);
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

        btn_next.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                increment++;
                loadList(increment);
                CheckEnable();
            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                increment--;
                loadList(increment);
                CheckEnable();
            }
        });

        backButtonClicked();
    }

    private void loadList(int number)
    {
        ArrayList<User> sort = new ArrayList<User>();
        title.setText("Page "+(number+1)+" of "+pageCount);

        int start = number * NUM_ITEMS_PAGE;
        for(int i=start;i<(start)+NUM_ITEMS_PAGE;i++)
        {
            if(i<userLists.size())
            {
                sort.add(userLists.get(i));
            }
            else
            {
                break;
            }
        }
        initializingUserList(sort);
    }

    private void CheckEnable()
    {
        if(increment+1 == pageCount)
        {
            btn_next.setEnabled(false);
            btn_prev.setEnabled(true);
        }
        else if(increment == 0)
        {
            btn_prev.setEnabled(false);
        }
        else
        {
            btn_prev.setEnabled(true);
            btn_next.setEnabled(true);
        }
    }

    private void backButtonClicked() {
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void initializingUserList(final List<User> users) {
        initList(users);
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equalsIgnoreCase("")){
                    initList(userLists);
                } else {
                    searchItem(charSequence.toString());
                }
//               customListAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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

    private void searchItem(String s) {
        List<User> users = new ArrayList<User>();
        for (int index=0; index<userLists.size(); index++) {
            if (userLists.get(index).getUsername().contains(s)) {
                users.add(userLists.get(index));
            }
        }
        initList(users);
    }

    private void initList(List<User> users) {
        customListAdapter = new CustomListAdapter(ViewUsersActivity.this, users);
        userList = findViewById(R.id.userList);
        userList.setAdapter(customListAdapter);
        userList.setTextFilterEnabled(true);
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
