package com.example.studenthub.activities;

import com.example.studenthub.R;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class DashboardActivity extends AppCompatActivity {

    MaterialCardView cardProfile, cardCourseReg, cardGpa, cardTimetable,
            cardELearning, cardAnnouncements, cardRecords, cardSettings, cardOnline, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_activity);

        // Initialize Cards
        cardProfile = findViewById(R.id.cardProfile);
        cardCourseReg = findViewById(R.id.cardCourseReg);
        cardGpa = findViewById(R.id.cardGpa);
        cardTimetable = findViewById(R.id.cardTimetable);
        cardELearning = findViewById(R.id.cardELearning);
        cardAnnouncements = findViewById(R.id.cardAnnouncements);
        cardRecords = findViewById(R.id.cardRecords);
        cardSettings = findViewById(R.id.cardSettings);
        cardOnline = findViewById(R.id.cardOnline);
        btnLogout = findViewById(R.id.btnLogout);

        // Navigation with transitions
        cardProfile.setOnClickListener(v -> navigateTo(ProfileActivity.class));
        cardCourseReg.setOnClickListener(v -> navigateTo(CourseRegistrationActivity.class));
        cardGpa.setOnClickListener(v -> navigateTo(GpaActivity.class));
        cardTimetable.setOnClickListener(v -> navigateTo(TimetableActivity.class));
        cardELearning.setOnClickListener(v -> navigateTo(ELearningActivity.class));
        cardAnnouncements.setOnClickListener(v -> navigateTo(AnnouncementsActivity.class));
        cardRecords.setOnClickListener(v -> navigateTo(StudentRecordsActivity.class));
        cardSettings.setOnClickListener(v -> navigateTo(SettingsActivity.class));
        cardOnline.setOnClickListener(v -> navigateTo(OnlineStudentsActivity.class));

        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, MainActivity.class));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });
    }

    private void navigateTo(Class<?> targetActivity) {
        startActivity(new Intent(this, targetActivity));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}