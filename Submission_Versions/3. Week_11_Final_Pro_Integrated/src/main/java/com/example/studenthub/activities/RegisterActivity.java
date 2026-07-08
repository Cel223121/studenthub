package com.example.studenthub.activities;

import com.example.studenthub.R;
import com.example.studenthub.database.DatabaseHelper;
import com.example.studenthub.events.EventLogger;
import com.example.studenthub.events.KeyboardController;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {

    EditText etName, etEmail, etCourse, etPassword, etYear, etPhone;
    Button btnRegister;
    ProgressBar progressRegister;

    DatabaseHelper databaseHelper;
    KeyboardController keyboardController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etCourse = findViewById(R.id.etCourse);
        etYear = findViewById(R.id.etYear);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);

        btnRegister = findViewById(R.id.btnRegister);
        progressRegister = findViewById(R.id.progressRegister);

        databaseHelper = new DatabaseHelper(this);
        keyboardController = new KeyboardController(this);

        // Keyboard Event: Handle Enter Key
        etPassword.setOnEditorActionListener((v, actionId, event) -> {
            keyboardController.onKeyPressed("Enter");
            btnRegister.performClick();
            return true;
        });

        btnRegister.setOnClickListener(v -> {

            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String course = etCourse.getText().toString().trim();
            String yearStr = etYear.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String rawPassword = etPassword.getText().toString();

            boolean valid = true;

            if(name.isEmpty()){
                etName.setError("Full name is required");
                valid = false;
            }

            if(email.isEmpty()){
                etEmail.setError("Email is required");
                valid = false;
            }
            else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                etEmail.setError("Enter a valid email address");
                valid = false;
            }

            if(course.isEmpty()){
                etCourse.setError("Course is required");
                valid = false;
            }

            if(yearStr.isEmpty()){
                etYear.setError("Year is required");
                valid = false;
            }

            if(phone.isEmpty()){
                etPhone.setError("Phone number is required");
                valid = false;
            }
            else if(phone.length() < 10){
                etPhone.setError("Enter a valid phone number");
                valid = false;
            }

            if(rawPassword.isEmpty()){
                etPassword.setError("Password is required");
                valid = false;
            }
            else if(rawPassword.length() < 6){
                etPassword.setError("Password must contain at least 6 characters");
                valid = false;
            }

            if(!valid){
                EventLogger.logEvent("Registration Validation Failed");
                return;
            }

            if(databaseHelper.emailExists(email)){
                etEmail.setError("This email is already registered");
                EventLogger.logEvent("Registration Failed: Email already exists");
                return;
            }

            progressRegister.setVisibility(View.VISIBLE);
            btnRegister.setEnabled(false);

            new Handler().postDelayed(() -> {
                String password = hashPassword(rawPassword);
                int year = Integer.parseInt(yearStr);

                EventLogger.logEvent("Attempting to register user: " + email);
                boolean inserted = databaseHelper.insertData(
                        name, email, course, year, phone, password);

                progressRegister.setVisibility(View.GONE);
                btnRegister.setEnabled(true);

                if(inserted) {
                    EventLogger.logEvent("Registration Successful for: " + email);
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("Registration Complete")
                            .setMessage("Your account has been created successfully.")
                            .setPositiveButton("Login", (dialog, which) -> {
                                startActivity(new Intent(
                                        RegisterActivity.this,
                                        MainActivity.class));
                                finish();
                            })
                            .setCancelable(false)
                            .show();

                } else {
                    EventLogger.logEvent("Registration Failed for: " + email);
                    new AlertDialog.Builder(RegisterActivity.this)
                            .setTitle("Registration Failed")
                            .setMessage("Unable to create your account.\nPlease try again.")
                            .setPositiveButton("OK", null)
                            .show();
                }
            }, 1500);
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