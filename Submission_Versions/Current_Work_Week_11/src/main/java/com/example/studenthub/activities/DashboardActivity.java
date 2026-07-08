package com.example.studenthub.activities;

import com.example.studenthub.R;
import com.example.studenthub.events.EventLogger;
import com.example.studenthub.events.GestureHandler;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studenthub.database.DatabaseHelper;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {

    MaterialCardView cardProfile, cardCourseReg, cardGpa, cardTimetable,
            cardELearning, cardAnnouncements, cardRecords, cardSettings, cardOnline, cardAttendance, cardAttendanceReport, cardCampusExplorer, btnLogout;

    private GestureDetector gestureDetector;

    // Dashboard TextViews
    TextView txtGreeting;
    TextView txtDate;
    TextView txtTime;
    TextView txtStudentCountStat;
    TextView txtAttendanceCount;
    TextView txtBuildingCount;
    TextView txtRecentActivity;
    TextView txtSystem;
    TextView txtNotificationBadge;

    private TextView txtSystemStatus;
    private TextView txtStudentCount;
    private TextView txtCurrentDate;
    private TextView txtCurrentTime;

    TextInputEditText etSearchDashboard;
    ProgressBar progressHealth;

    // Database
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_activity);

        databaseHelper = new DatabaseHelper(this);

        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);
        txtGreeting = findViewById(R.id.txtGreeting);
        txtStudentCountStat = findViewById(R.id.txtStudentCountStat);
        txtAttendanceCount = findViewById(R.id.txtAttendanceCount);
        txtBuildingCount = findViewById(R.id.txtBuildingCount);
        txtRecentActivity = findViewById(R.id.txtRecentActivity);
        txtSystem = findViewById(R.id.txtSystem);
        txtNotificationBadge = findViewById(R.id.txtNotificationBadge);

        txtSystemStatus = findViewById(R.id.txtSystemStatus);
        txtStudentCount = findViewById(R.id.txtStudentCount);
        txtCurrentDate = findViewById(R.id.txtCurrentDate);
        txtCurrentTime = findViewById(R.id.txtCurrentTime);

        etSearchDashboard = findViewById(R.id.etSearchDashboard);
        progressHealth = findViewById(R.id.progressHealth);

        // Initialize Gesture Detector
        gestureDetector = new GestureDetector(this, new GestureHandler(this));

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
        cardAttendance = findViewById(R.id.cardAttendance);
        cardAttendanceReport = findViewById(R.id.cardAttendanceReport);
        cardCampusExplorer = findViewById(R.id.cardCampusExplorer);
        btnLogout = findViewById(R.id.btnLogout);

        updateDashboard();
        setupSearch();
        animateDashboard();

        EventLogger.logEvent("Dashboard Opened");

        // Navigation with transitions
        cardProfile.setOnClickListener(v -> navigateTo(ProfileActivity.class));
        cardCourseReg.setOnClickListener(v -> navigateTo(CourseRegistrationActivity.class));
        cardGpa.setOnClickListener(v -> navigateTo(GpaActivity.class));
        cardTimetable.setOnClickListener(v -> navigateTo(TimetableActivity.class));
        cardELearning.setOnClickListener(v -> navigateTo(ELearningActivity.class));
        cardAnnouncements.setOnClickListener(v -> navigateTo(AnnouncementsActivity.class));
        cardRecords.setOnClickListener(v -> navigateTo(StudentRecordsActivity.class));
        cardSettings.setOnClickListener(v -> navigateTo(AboutActivity.class));
        cardOnline.setOnClickListener(v -> navigateTo(OnlineStudentsActivity.class));
        cardAttendance.setOnClickListener(v -> navigateTo(AttendanceActivity.class));
        cardAttendanceReport.setOnClickListener(v -> navigateTo(AttendanceReportActivity.class));
        cardCampusExplorer.setOnClickListener(v -> navigateTo(CampusExplorerActivity.class));

        // Long Press Gesture Listeners for Cards
        View.OnLongClickListener cardLongClickListener = v -> {
            EventLogger.logEvent("Long Press Detected on Card");
            Toast.makeText(DashboardActivity.this, "Long Press Detected", Toast.LENGTH_SHORT).show();
            return true;
        };

        cardProfile.setOnLongClickListener(cardLongClickListener);
        cardAnnouncements.setOnLongClickListener(cardLongClickListener);
        cardRecords.setOnLongClickListener(cardLongClickListener);

        btnLogout.setOnClickListener(v -> {

            EventLogger.logEvent("User Logout Triggered");

            getSharedPreferences(
                    "user_session",
                    MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply();

            startActivity(
                    new Intent(
                            DashboardActivity.this,
                            MainActivity.class));

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        gestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDashboard();
    }

    private void updateDashboard() {
        // Greeting & Time Logic
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        String greeting;
        if (hour < 12) {
            greeting = "Good Morning";
        } else if (hour < 17) {
            greeting = "Good Afternoon";
        } else {
            greeting = "Good Evening";
        }
        txtGreeting.setText(greeting);

        // Date & Time
        String today = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
                .format(calendar.getTime());
        txtDate.setText(today);

        String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault())
                .format(calendar.getTime());
        txtTime.setText(currentTime);

        // Quick Statistics
        txtStudentCountStat.setText(String.valueOf(databaseHelper.getStudentCount()));
        txtAttendanceCount.setText(String.valueOf(databaseHelper.getAttendanceCount()));
        txtBuildingCount.setText(String.valueOf(databaseHelper.getCampusBuildingCount()));

        // Live Header Info
        txtSystemStatus.setText("🟢 System Status : ONLINE");

        Calendar calendarNow = Calendar.getInstance();
        String headerDate = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
                .format(calendarNow.getTime());
        txtCurrentDate.setText(headerDate);

        String headerTime = new SimpleDateFormat("hh:mm a", Locale.getDefault())
                .format(calendarNow.getTime());
        txtCurrentTime.setText(headerTime);

        txtStudentCount.setText("Registered Students : " + databaseHelper.getStudentCount());

        // Recent Activity Feed
        txtRecentActivity.setText(
                "✔ Portal accessed: " + currentTime + "\n" +
                "✔ Database connection established\n" +
                "✔ Student records synced\n" +
                "✔ Campus Explorer module verified");

        // System Health Indicator
        try {
            boolean databaseReady = databaseHelper.getReadableDatabase() != null;
            if (databaseReady) {
                txtSystem.setText("🟢 System Healthy");
                progressHealth.setProgress(98);
            } else {
                txtSystem.setText("🔴 Database Error");
                progressHealth.setProgress(30);
            }
        } catch (Exception e) {
            txtSystem.setText("🔴 System Offline");
            progressHealth.setProgress(10);
        }

        // Update Notification Badge
        txtNotificationBadge.setText("5");
    }

    private void setupSearch() {
        etSearchDashboard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String search = s.toString().toLowerCase();

                cardProfile.setVisibility(search.isEmpty() || "profile".contains(search) ? View.VISIBLE : View.GONE);
                cardCourseReg.setVisibility(search.isEmpty() || "registration".contains(search) || "course".contains(search) ? View.VISIBLE : View.GONE);
                cardGpa.setVisibility(search.isEmpty() || "gpa".contains(search) || "calculator".contains(search) ? View.VISIBLE : View.GONE);
                cardTimetable.setVisibility(search.isEmpty() || "timetable".contains(search) || "schedule".contains(search) ? View.VISIBLE : View.GONE);
                cardELearning.setVisibility(search.isEmpty() || "elearning".contains(search) || "learning".contains(search) ? View.VISIBLE : View.GONE);
                cardAnnouncements.setVisibility(search.isEmpty() || "announcements".contains(search) || "news".contains(search) ? View.VISIBLE : View.GONE);
                cardRecords.setVisibility(search.isEmpty() || "records".contains(search) || "students".contains(search) ? View.VISIBLE : View.GONE);
                cardSettings.setVisibility(search.isEmpty() || "settings".contains(search) || "about".contains(search) ? View.VISIBLE : View.GONE);
                cardOnline.setVisibility(search.isEmpty() || "online".contains(search) || "live".contains(search) ? View.VISIBLE : View.GONE);
                cardAttendance.setVisibility(search.isEmpty() || "attendance".contains(search) ? View.VISIBLE : View.GONE);
                cardAttendanceReport.setVisibility(search.isEmpty() || "report".contains(search) || "summary".contains(search) ? View.VISIBLE : View.GONE);
                cardCampusExplorer.setVisibility(search.isEmpty() || "campus".contains(search) || "explorer".contains(search) || "gps".contains(search) ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void animateDashboard() {
        MaterialCardView[] cards = {cardProfile, cardCourseReg, cardGpa, cardTimetable, 
                cardELearning, cardAnnouncements, cardRecords, cardSettings, 
                cardOnline, cardAttendance, cardAttendanceReport, cardCampusExplorer};

        for (int i = 0; i < cards.length; i++) {
            cards[i].setAlpha(0f);
            cards[i].setTranslationY(50f);
            cards[i].animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(500)
                    .setStartDelay(i * 100L)
                    .start();
        }
    }

    private void navigateTo(Class<?> targetActivity) {
        EventLogger.logEvent("Navigating to: " + targetActivity.getSimpleName());
        startActivity(new Intent(this, targetActivity));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}