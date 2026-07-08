package com.example.studenthub.activities;

import com.example.studenthub.R;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ELearningActivity extends AppCompatActivity {

    Button btnNotes, btnAssignments, btnVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elearning);

        btnNotes = findViewById(R.id.btnNotes);
        btnAssignments = findViewById(R.id.btnAssignments);
        btnVideos = findViewById(R.id.btnVideos);

        btnNotes.setOnClickListener(v -> openResources("Lecture Notes"));
        btnAssignments.setOnClickListener(v -> openResources("Assignments"));
        btnVideos.setOnClickListener(v -> openResources("Learning Videos"));
        
        findViewById(R.id.toolbar).setOnClickListener(v -> finish());
    }

    private void openResources(String type) {
        Intent intent = new Intent(this, ElearningResourceActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}