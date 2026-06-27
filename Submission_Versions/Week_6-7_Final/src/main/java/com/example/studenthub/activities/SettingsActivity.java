package com.example.studenthub.activities;

import com.example.studenthub.R;
import com.example.studenthub.database.DatabaseHelper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SettingsActivity extends AppCompatActivity {

    Switch switchNotifications;
    Switch switchDarkMode;

    Button btnChangePassword, btnClearSession, btnAbout;
    
    SharedPreferences settingsPref;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smartsettings);

        settingsPref = getSharedPreferences("app_settings", MODE_PRIVATE);
        db = new DatabaseHelper(this);

        switchNotifications = findViewById(R.id.switchNotifications);
        switchDarkMode = findViewById(R.id.switchDarkMode);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnClearSession = findViewById(R.id.btnClearSession);
        btnAbout = findViewById(R.id.btnAbout);

        // Load Current States
        boolean isNotificationsEnabled = settingsPref.getBoolean("notifications", true);
        boolean isDarkMode = settingsPref.getBoolean("dark_mode", false);
        
        switchNotifications.setChecked(isNotificationsEnabled);
        switchDarkMode.setChecked(isDarkMode);

        // Notification Toggle
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            settingsPref.edit().putBoolean("notifications", isChecked).apply();
            Toast.makeText(this, isChecked ? "Notifications Enabled" : "Notifications Disabled", Toast.LENGTH_SHORT).show();
        });

        // Dark Mode Toggle
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            settingsPref.edit().putBoolean("dark_mode", isChecked).apply();
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            Toast.makeText(this, "Applying Theme...", Toast.LENGTH_SHORT).show();
        });

        // Change Password Dialog
        btnChangePassword.setOnClickListener(v -> showChangePasswordDialog());

        // Clear Session (Logout)
        btnClearSession.setOnClickListener(v -> {
            getSharedPreferences("user_session", MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply();
            
            Toast.makeText(this, "Session Cleared. Logging out...", Toast.LENGTH_SHORT).show();
            
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        // About Portal Dialog
        btnAbout.setOnClickListener(v -> showAboutDialog());
    }

    private void showChangePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_change_password, null);
        
        EditText etNewPass = dialogView.findViewById(R.id.etNewPassword);
        Button btnSubmit = dialogView.findViewById(R.id.btnSubmitPassword);
        
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        btnSubmit.setOnClickListener(v -> {
            String rawPass = etNewPass.getText().toString().trim();
            if (rawPass.isEmpty()) {
                etNewPass.setError("Password cannot be empty");
                return;
            }

            String hashedPass = hashPassword(rawPass);
            String email = getSharedPreferences("user_session", MODE_PRIVATE)
                           .getString("logged_email", "");

            if (email.isEmpty()) {
                Toast.makeText(this, "Session Expired. Please Login again.", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                if (db.updatePassword(email, hashedPass)) {
                    Toast.makeText(this, "Password Updated Successfully!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(this, "Update Failed. Try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    private void showAboutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("About StudentHub Pro")
                .setMessage("StudentHub Pro v1.0\n\nDeveloper: Celestine Nguhi\n\nA professional Academic Management Portal designed for modern universities.\n\nDeveloped for Excellence.")
                .setPositiveButton("Dismiss", null)
                .setIcon(R.drawable.ic_book)
                .show();
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return password;
    }
}