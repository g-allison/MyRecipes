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

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";

    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private Button mBtnSignup;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // if recently created user, doesn't skip to main activity
        String name = "";
        try {
            name = getIntent().getStringExtra("new account");
            if (name != null) {
                // tells user that an account has been made
                Toast.makeText(this, getResources().getString(R.string.new_account_created), Toast.LENGTH_LONG).show();
            }
            Log.i(TAG, "onCreate try: recentlyCreated status " + name);

        } catch (Exception e){
            Log.i(TAG, "onCreate catch: recentlyCreated status " + name);
        }

        if (ParseUser.getCurrentUser() != null && name == null) {
            goMainActivity();
        }

        mEtUsername = findViewById(R.id.etUsername);
        mEtPassword = findViewById(R.id.etPassword);
        mBtnLogin = findViewById(R.id.btnLogin);
        mBtnSignup = findViewById(R.id.btnSignup);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login button");
                String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();
                loginUser(username, password);
            }
        });

        mBtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login button");
                signupUser();
            }
        });
    }

    private void signupUser() {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
        Log.i(TAG, "redirecting to signup page");
    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "attempting to login user " + username);

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    // TODO: better error handling
                    Toast.makeText(LoginActivity.this, "account does not exist", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "issue with login", e);
                    return;
                }
                goMainActivity();
                Toast.makeText(LoginActivity.this, "successfully logged in!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
