package com.example.studenthub.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.studenthub.R;

public class SplashActivity extends AppCompatActivity {

    ImageView logo;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences pref =
                getSharedPreferences("app_settings", MODE_PRIVATE);

        boolean dark =
                pref.getBoolean("dark_mode", false);

        if (dark) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.logo);
        title = findViewById(R.id.title);

        Animation logoAnim =
                AnimationUtils.loadAnimation(this,
                        R.anim.logo_animation);

        Animation textAnim =
                AnimationUtils.loadAnimation(this,
                        R.anim.text_animation);

        logo.startAnimation(logoAnim);
        title.startAnimation(textAnim);

        new Handler().postDelayed(() -> {

            // Navigating to MainActivity to handle Auto-Login session check
            Intent intent =
                    new Intent(
                            SplashActivity.this,
                            MainActivity.class);

            startActivity(intent);

            overridePendingTransition(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out);

            finish();

        }, 2500);

    }

}