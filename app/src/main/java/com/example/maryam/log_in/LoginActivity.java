package com.example.maryam.log_in;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maryam.log_in.dto.LoginUser;
import com.example.maryam.log_in.dto.User;
import com.example.maryam.log_in.resource.RealmInstanceGenerator;
import com.example.maryam.log_in.resource.RetrofitGenerator;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private Button signUp;
    private EditText username;
    private EditText password;
    private TextView result;
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
        setContentView(R.layout.activity_login);
        Realm.init(this);
        onLoginButtonClicked();
        onSignUpButtonClicked();
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }

    public void onSignUpButtonClicked() {
        signUp = findViewById(R.id.signUpBtn);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ProfileActivity.class).putExtra("string", "Create");
                startActivity(intent);
            }
        });
    }

    public void onLoginButtonClicked() {
        login = findViewById(R.id.loginBtn);
        username = findViewById(R.id.usernameText);
        password = findViewById(R.id.passwordText);
        result = findViewById(R.id.result);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean userNameIsEmpty = username.getText().toString().length() == 0;
                boolean passwordIsEmpty = password.getText().toString().length() == 0;
                if (userNameIsEmpty || passwordIsEmpty) {
                    if (userNameIsEmpty) {
                        username.setError("Username is required!");
                    }
                    if (passwordIsEmpty) {
                        password.setError("Password is required!");
                    }
                } else {
                    UserApi userApi = getRetrofitGenerator().generateRetrofit().create(UserApi.class);
                    Call<User> call = userApi.validateUser(username.getText().toString(), password.getText().toString());
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Username or Password is invalid!", Toast.LENGTH_LONG).show();
                                return;
                            }
                            User user = response.body();
                            LoginUser loginUser = new LoginUser();
                            loginUser.setUsername(user.getUsername());
                            Realm realm = getRealmInstanceGenerator().generateRealmInstance();
                            realm.beginTransaction();
                            realm.deleteAll();
                            realm.copyToRealmOrUpdate(loginUser);
                            realm.commitTransaction();
                            realm.close();
                            username.setText("");
                            password.setText("");
                            Toast.makeText(LoginActivity.this, user.getUsername() + " logged in successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            result.setText(t.getMessage());
                        }
                    });

                }
            }
        });
    }
}
