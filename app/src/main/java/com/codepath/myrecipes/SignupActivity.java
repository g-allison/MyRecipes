package com.codepath.myrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import static android.content.ContentValues.TAG;

public class SignupActivity extends AppCompatActivity {

    private EditText mEtUsername;
    private EditText mEtPassword;
    private EditText mEtConfirmPassword;
    private Button mBtnSignup;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mEtUsername = findViewById(R.id.etUsername);
        mEtPassword = findViewById(R.id.etPassword);
        mEtConfirmPassword = findViewById(R.id.etConfirmPassword);
        mBtnSignup = findViewById(R.id.btnSignup);

        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();
                String passwordConfirm = mEtConfirmPassword.getText().toString();

                // passwords must equal each other
                if (!password.equals(passwordConfirm)) {
                    Toast.makeText(SignupActivity.this, getResources().getString(R.string.matching_passwords), Toast.LENGTH_SHORT).show();
                } else {
                    createUser(username, password);
                }
            }
        });
    }

    // creates new ParseUser based on entered username and password
    private void createUser(String username, String password) {
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // Redirects to log in page
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    intent.putExtra("new account", "true");
                    startActivity(intent);
                    finish();

                } else {
                    // Sign up didn't exceed
                    Log.e(TAG, "done: ", e);
                }
            }
        });

    }
}
