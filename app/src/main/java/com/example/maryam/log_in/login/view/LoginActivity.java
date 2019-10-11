package com.example.maryam.log_in.login.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.maryam.log_in.R;
import com.example.maryam.log_in.item.view.MainActivity;
import com.example.maryam.log_in.login.presenter.LoginPresenter;
import com.example.maryam.log_in.login.presenter.LoginPresenterImpl;
import com.example.maryam.log_in.user.view.ProfileActivity;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private Button login;
    private Button signUp;
    private EditText username;
    private EditText password;
    private TextView result;
    private LoginPresenter loginPresenter;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenterImpl(this);
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
                loginPresenter.loginCredential(username.getText().toString().trim(), password.getText().toString().trim());
            }
        });
    }

    @Override
    public void showErrorOnUsername(String message) {
        username.setError("Username is required!");
    }

    @Override
    public void showErrorOnPassword(String message) {
        password.setError("Password is required!");
    }

    @Override
    public void actOnSuccessfulLogin(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void showOnNotSuccessfulLogin(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void clearFields() {
        username.setText("");
        password.setText("");
        actOnSuccessfulLogin("User is login now");
    }
}
