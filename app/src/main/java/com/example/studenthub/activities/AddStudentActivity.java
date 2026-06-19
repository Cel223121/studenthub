package com.example.studenthub.activities;

import com.example.studenthub.R;
import com.example.studenthub.database.DatabaseHelper;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class AddStudentActivity extends AppCompatActivity {

    EditText etName, etEmail, etCourse, etPassword, etYear, etPhone;
    TextInputLayout tilName, tilEmail, tilCourse, tilPassword, tilYear, tilPhone;
    Button btnSave, btnClear;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_student);

        // Initialize Views
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etCourse = findViewById(R.id.etCourse);
        etYear = findViewById(R.id.etYear);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        
        tilName = findViewById(R.id.tilName);
        tilEmail = findViewById(R.id.tilEmail);
        tilCourse = findViewById(R.id.tilCourse);
        tilYear = findViewById(R.id.tilYear);
        tilPhone = findViewById(R.id.tilPhone);
        tilPassword = findViewById(R.id.tilPassword);

        btnSave = findViewById(R.id.btnSaveStudent);
        btnClear = findViewById(R.id.btnClearForm);

        db = new DatabaseHelper(this);

        btnSave.setOnClickListener(v -> {
            if (validateForm()) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String course = etCourse.getText().toString();
                int year = Integer.parseInt(etYear.getText().toString());
                String phone = etPhone.getText().toString();
                String password = etPassword.getText().toString();

                if (db.insertData(name, email, course, year, phone, password)) {
                    Toast.makeText(this, "Student Added Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Addition Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnClear.setOnClickListener(v -> {
            etName.setText("");
            etEmail.setText("");
            etCourse.setText("");
            etYear.setText("");
            etPhone.setText("");
            etPassword.setText("");
            
            tilName.setError(null);
            tilEmail.setError(null);
            tilCourse.setError(null);
            tilYear.setError(null);
            tilPhone.setError(null);
            tilPassword.setError(null);
        });
        
        findViewById(R.id.toolbar).setOnClickListener(v -> finish());
    }

    private boolean validateForm() {
        boolean isValid = true;

        if (TextUtils.isEmpty(etName.getText())) {
            tilName.setError("Name is required");
            isValid = false;
        } else {
            tilName.setError(null);
        }

        if (TextUtils.isEmpty(etEmail.getText())) {
            tilEmail.setError("Email is required");
            isValid = false;
        } else {
            tilEmail.setError(null);
        }

        if (TextUtils.isEmpty(etCourse.getText())) {
            tilCourse.setError("Course is required");
            isValid = false;
        } else {
            tilCourse.setError(null);
        }

        if (TextUtils.isEmpty(etYear.getText())) {
            tilYear.setError("Year is required");
            isValid = false;
        } else {
            tilYear.setError(null);
        }

        if (TextUtils.isEmpty(etPhone.getText())) {
            tilPhone.setError("Phone is required");
            isValid = false;
        } else {
            tilPhone.setError(null);
        }

        if (TextUtils.isEmpty(etPassword.getText())) {
            tilPassword.setError("Password is required");
            isValid = false;
        } else {
            tilPassword.setError(null);
        }

        return isValid;
    }
}