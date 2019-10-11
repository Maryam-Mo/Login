package com.example.maryam.log_in.user.view;

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
import com.example.maryam.log_in.api.UserApi;
import com.example.maryam.log_in.dto.User;
import com.example.maryam.log_in.resource.RealmInstanceGenerator;
import com.example.maryam.log_in.resource.RetrofitGenerator;
import com.example.maryam.log_in.login.view.LoginActivity;
import com.example.maryam.log_in.user.presenter.UserPresenter;
import com.example.maryam.log_in.user.presenter.UserPresenterImpl;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity implements UserProfile{
    private Button saveBtn;
    private EditText username;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private boolean onEdit;
    private TextView result;
    private User selectedUser;
    private Button delete;
    private String loginUser;
    private UserPresenter userPresenter;

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
        checkIntentContent(intent);
        userPresenter = new UserPresenterImpl(this);
        backButtonClicked();
        onSaveButtonClicked();
    }

    private void checkIntentContent(Intent intent) {
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
    }

    private void backButtonClicked() {
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void onSaveButtonClicked() {
        saveBtn = findViewById(R.id.saveBtn);
        result = findViewById(R.id.resultView);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedUser != null && selectedUser.getId() != null) {
                    userPresenter.updateUser(firstName.getText().toString(), lastName.getText().toString(),
                            username.getText().toString(), password.getText().toString(), selectedUser.getId());
                } else {
                    userPresenter.saveUser(firstName.getText().toString(), lastName.getText().toString(),
                            username.getText().toString(), password.getText().toString());
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
            userPresenter.delete(selectedUser);
            }
        });
    }

    @Override
    public void showErrorOnEmptyFirstNameField() {
        firstName.setError("First Name is required!");
    }

    @Override
    public void showErrorOnEmptyLastNameField() {
        lastName.setError("Last Name is required!");
    }

    @Override
    public void showErrorOnEmptyUsernameField() {
        username.setError("Username is required!");
    }

    @Override
    public void showErrorOnEmptyPasswordField() {
        password.setError("Password is required!");
    }

    public void onSuccessfulSaveUser() {
        Toast.makeText(ProfileActivity.this, "New User is created successfully!", Toast.LENGTH_SHORT).show();
        clearFields();
    }

    public void onSuccessfulUpdateUser() {
        Toast.makeText(ProfileActivity.this, "User is updated successfully!", Toast.LENGTH_SHORT).show();
        clearFields();
    }

    @Override
    public void showOnNotSuccessfulSaveUser() {
        Toast.makeText(ProfileActivity.this, "There is a problem :D", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showOnError(Throwable throwable) {
        Toast.makeText(ProfileActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showOnNotSuccessfulUpdateUser() {
        Toast.makeText(ProfileActivity.this, "There is a problem :D", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteAcitivity() {
        Toast.makeText(ProfileActivity.this, selectedUser.getUsername() + " has been deleted! Please log-in with another user!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
