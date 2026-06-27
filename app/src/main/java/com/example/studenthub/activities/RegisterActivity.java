package com.example.studenthub.activities;

import com.example.studenthub.R;
import com.example.studenthub.database.DatabaseHelper;
import com.example.studenthub.events.EventLogger;
import com.example.studenthub.events.KeyboardController;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {

    EditText etName, etEmail, etCourse, etPassword, etYear, etPhone;
    Button btnRegister;

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

        databaseHelper = new DatabaseHelper(this);
        keyboardController = new KeyboardController(this);

        // Keyboard Event: Handle Enter Key
        etPassword.setOnEditorActionListener((v, actionId, event) -> {
            keyboardController.onKeyPressed("Enter");
            btnRegister.performClick();
            return true;
        });

        btnRegister.setOnClickListener(v -> {

            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String course = etCourse.getText().toString();
            String yearStr = etYear.getText().toString();
            String phone = etPhone.getText().toString();
            String rawPassword = etPassword.getText().toString();

            // Use KeyboardController for validation
            if (!keyboardController.validateInput(name, "Name") ||
                !keyboardController.validateEmail(email) ||
                !keyboardController.validateInput(course, "Course") ||
                !keyboardController.validateInput(yearStr, "Year") ||
                !keyboardController.validateInput(phone, "Phone") ||
                !keyboardController.validateInput(rawPassword, "Password")) {
                
                EventLogger.logEvent("Registration Validation Failed");
                return;
            }

            String password = hashPassword(rawPassword);
            int year = Integer.parseInt(yearStr);
            
            EventLogger.logEvent("Attempting to register user: " + email);
            boolean inserted = databaseHelper.insertData(
                    name, email, course, year, phone, password);

            if(inserted) {
                EventLogger.logEvent("Registration Successful for: " + email);
                Toast.makeText(RegisterActivity.this,
                        "Registration Successful",
                        Toast.LENGTH_SHORT).show();

                startActivity(new Intent(
                        RegisterActivity.this,
                        MainActivity.class));

            } else {
                EventLogger.logEvent("Registration Failed for: " + email);
                Toast.makeText(RegisterActivity.this,
                        "Registration Failed",
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