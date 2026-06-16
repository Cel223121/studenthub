package com.example.studenthub.activities;

import com.example.studenthub.R;
import com.example.studenthub.database.DatabaseHelper;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    TextView tvName, tvCourse, tvEmail;
    Button btnBack;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvName = findViewById(R.id.tvProfileName);
        tvCourse = findViewById(R.id.tvProfileCourse);
        tvEmail = findViewById(R.id.tvProfileEmail);
        btnBack = findViewById(R.id.btnBackProfile);

        db = new DatabaseHelper(this);

        loadProfile();

        btnBack.setOnClickListener(v -> finish());
    }

    private void loadProfile() {
        String loggedEmail = getSharedPreferences("user_session", MODE_PRIVATE)
                .getString("logged_email", "");

        if (loggedEmail.isEmpty()) {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor cursor = db.getStudentByEmail(loggedEmail);
        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(1);
            String email = cursor.getString(2);
            String course = cursor.getString(3);

            tvName.setText("Name: " + name);
            tvEmail.setText("Email: " + email);
            tvCourse.setText("Course: " + course);
            
            cursor.close();
        } else {
            Toast.makeText(this, "Profile not found", Toast.LENGTH_SHORT).show();
        }
    }
}