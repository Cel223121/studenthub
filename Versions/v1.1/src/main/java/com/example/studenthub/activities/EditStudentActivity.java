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

public class EditStudentActivity extends AppCompatActivity {

    EditText etName, etEmail, etCourse, etYear, etPhone;
    TextInputLayout tilName, tilEmail, tilCourse, tilYear, tilPhone;
    Button btnUpdate;
    DatabaseHelper db;
    int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);

        db = new DatabaseHelper(this);

        // Initialize Views
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etCourse = findViewById(R.id.etCourse);
        etYear = findViewById(R.id.etYear);
        etPhone = findViewById(R.id.etPhone);
        
        tilName = findViewById(R.id.tilName);
        tilEmail = findViewById(R.id.tilEmail);
        tilCourse = findViewById(R.id.tilCourse);
        tilYear = findViewById(R.id.tilYear);
        tilPhone = findViewById(R.id.tilPhone);
        
        btnUpdate = findViewById(R.id.btnUpdateStudent);

        // Get Data from Intent
        String idStr = getIntent().getStringExtra("id");
        if (idStr != null) {
            studentId = Integer.parseInt(idStr);
        }
        
        etName.setText(getIntent().getStringExtra("name"));
        etEmail.setText(getIntent().getStringExtra("email"));
        etCourse.setText(getIntent().getStringExtra("course"));
        etYear.setText(String.valueOf(getIntent().getIntExtra("year", 1)));
        etPhone.setText(getIntent().getStringExtra("phone"));

        btnUpdate.setOnClickListener(v -> {
            if (validateForm()) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String course = etCourse.getText().toString();
                int year = Integer.parseInt(etYear.getText().toString());
                String phone = etPhone.getText().toString();

                if (db.updateStudent(studentId, name, email, course, year, phone, "")) {
                    Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
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

        return isValid;
    }
}