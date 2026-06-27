package com.example.studenthub.activities;

import com.example.studenthub.R;
import com.example.studenthub.database.DatabaseHelper;
import com.example.studenthub.events.EventLogger;
import com.example.studenthub.events.KeyboardController;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    EditText etLoginEmail, etLoginPassword;
    Button btnLogin;
    TextView txtRegister;

    DatabaseHelper databaseHelper;
    KeyboardController keyboardController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String savedEmail = getSharedPreferences(
                "user_session",
                MODE_PRIVATE)
                .getString("logged_email", null);

        if(savedEmail != null){
            EventLogger.logEvent("Auto-login for: " + savedEmail);
            startActivity(new Intent(
                    MainActivity.this,
                    DashboardActivity.class));

            finish();
        }

        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);

        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);

        databaseHelper = new DatabaseHelper(this);
        keyboardController = new KeyboardController(this);

        txtRegister.setOnClickListener(v -> {
            startActivity(new Intent(
                    MainActivity.this,
                    RegisterActivity.class));
        });

        // Keyboard Event: Handle Enter Key
        etLoginPassword.setOnEditorActionListener((v, actionId, event) -> {
            keyboardController.onKeyPressed("Enter");
            btnLogin.performClick();
            return true;
        });

        btnLogin.setOnClickListener(v -> {

            String email = etLoginEmail.getText().toString();
            String rawPassword = etLoginPassword.getText().toString();

            // Validation using KeyboardController
            if (!keyboardController.validateInput(email, "Email") ||
                !keyboardController.validateInput(rawPassword, "Password")) {
                EventLogger.logEvent("Login Validation Failed");
                return;
            }

            String password = hashPassword(rawPassword);

            EventLogger.logEvent("Login Attempt: " + email);
            boolean checkLogin = databaseHelper.checkLogin(
                    email, password);

            if(checkLogin) {
                EventLogger.logEvent("Login Successful: " + email);
                // Create Session
                getSharedPreferences("user_session", MODE_PRIVATE)
                        .edit()
                        .putString("logged_email", email)
                        .apply();

                Toast.makeText(MainActivity.this,
                        "Login Successful",
                        Toast.LENGTH_SHORT).show();

                startActivity(new Intent(
                        MainActivity.this,
                        DashboardActivity.class));

            } else {
                EventLogger.logEvent("Login Failed: " + email);
                Toast.makeText(MainActivity.this,
                        "Invalid Email or Password",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return password;
    }
}