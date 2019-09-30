package com.example.maryam.log_in;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maryam.log_in.dto.User;
import com.example.maryam.log_in.resource.RealmInstanceGenerator;
import com.example.maryam.log_in.resource.RetrofitGenerator;
import com.example.maryam.log_in.login.view.LoginActivity;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private Button saveBtn;
    private EditText username;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private boolean onEdit;
    private TextView result;
    private User selectedUser;
    private Button delete;
    private RetrofitGenerator retrofitGenerator;
    private RealmInstanceGenerator realmInstanceGenerator;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        username = findViewById(R.id.usernameTxt);
        password = findViewById(R.id.passwordTxt);
        firstName = findViewById(R.id.firstNameTxt);
        lastName = findViewById(R.id.lastNameTxt);
        Intent intent = getIntent();
        if (intent.hasExtra("object")){
            selectedUser = intent.getParcelableExtra("object");
            username.setText(selectedUser.getUsername());
            password.setText(selectedUser.getPassword());
            firstName.setText(selectedUser.getFirstName());
            lastName.setText(selectedUser.getLastName());
            onDeleteButtonClicked();
            onEdit = true;
        } else if (intent.hasExtra("string")) {
            username.setText("");
            password.setText("");
            firstName.setText("");
            lastName.setText("");
        }
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        onSaveButtonClicked();
        if (selectedUser != null) {
            onDeleteButtonClicked();
        }
    }

    public void onSaveButtonClicked() {
        saveBtn = findViewById(R.id.saveBtn);
        result = findViewById(R.id.resultView);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean userNameIsEmpty = username.getText().toString().length() == 0;
                boolean passwordIsEmpty = password.getText().toString().length() == 0;
                boolean firstNameIsEmpty = firstName.getText().toString().length() == 0;
                boolean lastNameIsEmpty = lastName.getText().toString().length() == 0;
                if (userNameIsEmpty || passwordIsEmpty || firstNameIsEmpty || lastNameIsEmpty) {
                    if (firstNameIsEmpty) {
                        firstName.setError("First Name is required!");
                    }
                    if (lastNameIsEmpty) {
                        lastName.setError("Last Name is required!");
                    }
                    if (userNameIsEmpty) {
                        username.setError("Username is required!");
                    }
                    if (passwordIsEmpty) {
                        password.setError("Password is required!");
                    }
                } else {
                    User userToSave = new User();
                    userToSave.setFirstName(firstName.getText().toString());
                    userToSave.setLastName(lastName.getText().toString());
                    userToSave.setUsername(username.getText().toString());
                    userToSave.setPassword(password.getText().toString());
                    UserApi userApi = RetrofitGenerator.INSTANCE.generateRetrofit().create(UserApi.class);
                    if (!onEdit) {
                        Call<User> createCall = userApi.createUser(userToSave);
                        createCall.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (!response.isSuccessful()) {
                                    result.setText(response.message());
                                    return;
                                }
                                final User receivedUser = response.body();
                                Realm realm = null;
                                try {
                                    realm = RealmInstanceGenerator.INSTANCE.generateRealmInstance();
                                    realm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            realm.insertOrUpdate(receivedUser);
                                        }
                                    });
                                } finally {
                                    if(realm != null) {
                                        realm.close();
                                    }
                                }
                                Toast.makeText(ProfileActivity.this, "New user is created successfully!", Toast.LENGTH_SHORT).show();
                                clearFields();
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                result.setText(t.getMessage());
                            }
                        });
                    } else {
                        userToSave.setId(selectedUser.getId());
                        Call<User> updateCall = userApi.updateUser(userToSave);
                        updateCall.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (!response.isSuccessful()) {
                                    result.setText(response.message());
                                    return;
                                }
                                final User receivedUser = response.body();
                                Realm realm = null;
                                try {
                                    realm = RealmInstanceGenerator.INSTANCE.generateRealmInstance();
                                    realm.executeTransaction(new Realm.Transaction() {
                                        @Override
                                        public void execute(Realm realm) {
                                            realm.insertOrUpdate(receivedUser);
                                        }
                                    });
                                } finally {
                                    if(realm != null) {
                                        realm.close();
                                    }
                                }
                                Toast.makeText(ProfileActivity.this, "User is updated successfully!", Toast.LENGTH_SHORT).show();
                                clearFields();
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                result.setText(t.getMessage());
                            }
                        });
                    }
                }
            }
        });
    }

    private void clearFields() {
        firstName.setText("");
        lastName.setText("");
        username.setText("");
        password.setText("");
        onEdit = false;
    }

    public void onDeleteButtonClicked() {
        delete = findViewById(R.id.deleteBtn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserApi userApi = RetrofitGenerator.INSTANCE.generateRetrofit().create(UserApi.class);
            Call<Void> call = userApi.delete(selectedUser.getId().toString());
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (!response.isSuccessful()) {
                        return;
                    }
                    Realm realm = RealmInstanceGenerator.INSTANCE.generateRealmInstance();
                    realm.beginTransaction();
                    User user = realm.where(User.class).equalTo("id", selectedUser.getId()).findFirst();
                    user.deleteFromRealm();
                    realm.commitTransaction();
                    realm.close();
                    Toast.makeText(ProfileActivity.this, selectedUser.getUsername() + " has been deleted! Please log-in with another user!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
            }
        });
    }
}
