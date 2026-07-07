package com.example.studenthub.activities;

import com.example.studenthub.R;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CourseRegistrationActivity extends AppCompatActivity {

    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courseregistration);

        btnRegister = findViewById(R.id.btnRegisterUnits);
        btnRegister.setOnClickListener(v -> {
            Toast.makeText(this, "Courses Registered Successfully", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}