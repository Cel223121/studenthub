package com.example.studenthub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText etName, etEmail, etCourse, etPassword;
    Button btnRegister;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etCourse = findViewById(R.id.etCourse);
        etPassword = findViewById(R.id.etPassword);

        btnRegister = findViewById(R.id.btnRegister);

        databaseHelper = new DatabaseHelper(this);

        btnRegister.setOnClickListener(v -> {

            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String course = etCourse.getText().toString();
            String password = etPassword.getText().toString();

            if(name.isEmpty() || email.isEmpty()
                    || course.isEmpty() || password.isEmpty()) {

                Toast.makeText(RegisterActivity.this,
                        "Fill all fields",
                        Toast.LENGTH_SHORT).show();

            } else {

                boolean inserted = databaseHelper.insertData(
                        name, email, course, password);

                if(inserted) {

                    Toast.makeText(RegisterActivity.this,
                            "Registration Successful",
                            Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(
                            RegisterActivity.this,
                            MainActivity.class));

                } else {

                    Toast.makeText(RegisterActivity.this,
                            "Registration Failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}