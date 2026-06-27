package com.example.studenthub;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    Switch switchNotifications;
    Switch switchDarkMode;

    Button btnChangePassword;
    Button btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smartsettings);

        switchNotifications = findViewById(R.id.switchNotifications);
        switchDarkMode = findViewById(R.id.switchDarkMode);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        btnAbout = findViewById(R.id.btnAbout);

        btnChangePassword.setOnClickListener(v ->
                Toast.makeText(
                        this,
                        "Password Change Feature Coming Soon",
                        Toast.LENGTH_SHORT
                ).show());

        btnAbout.setOnClickListener(v ->
                Toast.makeText(
                        this,
                        "StudentHub Version 1.0",
                        Toast.LENGTH_LONG
                ).show());
    }
}