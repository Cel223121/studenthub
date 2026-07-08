package com.example.studenthub;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FeesActivity extends AppCompatActivity {

    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);

        btnBack = findViewById(R.id.btnBackFees);
        btnBack.setOnClickListener(v -> finish());
    }
}