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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private Button signUp;
    private EditText username;
    private EditText password;
    private TextView result;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://172.16.1.27:8070/api/user/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    UserApi userApi = retrofit.create(UserApi.class);
                    Call<User> call = userApi.validateUser(username.getText().toString(), password.getText().toString());
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Username or Password is invalid!", Toast.LENGTH_LONG).show();
                                return;
                            }
                            User user = response.body();
                            username.setText("");
                            password.setText("");
                            Toast.makeText(LoginActivity.this, user.getUsername() + " logged in successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class).putExtra("object", user);
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
