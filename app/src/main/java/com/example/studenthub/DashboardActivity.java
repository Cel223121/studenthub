package com.example.studenthub;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    Button btnProfile, btnFees, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_acivity);

        btnProfile = findViewById(R.id.btnProfile);
        btnFees = findViewById(R.id.btnFees);
        btnLogout = findViewById(R.id.btnLogout);

        btnProfile.setOnClickListener(v -> {

            startActivity(new Intent(
                    DashboardActivity.this,
                    ProfileActivity.class));
        });

        btnFees.setOnClickListener(v -> {

            startActivity(new Intent(
                    DashboardActivity.this,
                    FeesActivity.class));
        });

        btnLogout.setOnClickListener(v -> {

            startActivity(new Intent(
                    DashboardActivity.this,
                    MainActivity.class));

            finish();
        });
    }
}