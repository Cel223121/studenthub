package com.example.studenthub.activities;

import com.example.studenthub.R;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class StudentRecordsActivity extends AppCompatActivity {

    MaterialCardView btnAdd, btnView, btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_records);

        btnAdd = findViewById(R.id.btnGoToAdd);
        btnView = findViewById(R.id.btnGoToView);
        btnSearch = findViewById(R.id.btnGoToSearch);

        btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, AddStudentActivity.class));
        });

        btnView.setOnClickListener(v -> {
            startActivity(new Intent(this, ViewStudentActivity.class));
        });

        btnSearch.setOnClickListener(v -> {
            // Opening view activity as it has the search bar integrated
            startActivity(new Intent(this, ViewStudentActivity.class));
        });

        findViewById(R.id.toolbar).setOnClickListener(v -> finish());
    }
}