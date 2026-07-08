package com.example.studenthub.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studenthub.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setTitle("About StudentHub Pro");
    }
}