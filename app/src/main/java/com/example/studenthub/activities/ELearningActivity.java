package com.example.studenthub.activities;

import com.example.studenthub.R;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

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

        btnNotes.setOnClickListener(v -> Toast.makeText(this, "Opening Lecture Notes...", Toast.LENGTH_SHORT).show());
        btnAssignments.setOnClickListener(v -> Toast.makeText(this, "Loading Assignments...", Toast.LENGTH_SHORT).show());
        btnVideos.setOnClickListener(v -> Toast.makeText(this, "Streaming Learning Videos...", Toast.LENGTH_SHORT).show());
    }
}