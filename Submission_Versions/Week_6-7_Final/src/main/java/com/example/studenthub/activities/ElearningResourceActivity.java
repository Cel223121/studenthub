package com.example.studenthub.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studenthub.R;
import com.example.studenthub.adapters.ElearningResourceAdapter;
import com.example.studenthub.models.ElearningResource;

import java.util.ArrayList;
import java.util.List;

public class ElearningResourceActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ElearningResourceAdapter adapter;
    List<ElearningResource> list;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elearning_resource);

        type = getIntent().getStringExtra("type");
        
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (type != null) {
            toolbar.setTitle(type);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerViewResources);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        loadData();

        adapter = new ElearningResourceAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    private void loadData() {
        if ("Lecture Notes".equals(type)) {
            list.add(new ElearningResource("Java Fundamentals", "Comprehensive notes on Java syntax and OOP.", "https://docs.oracle.com/javase/tutorial/"));
            list.add(new ElearningResource("Mobile Dev with Android", "Introduction to Android Studio and Activity lifecycle.", "https://developer.android.com/guide"));
            list.add(new ElearningResource("Database Design", "Normalization, SQL queries and ER Diagrams.", "https://www.w3schools.com/sql/"));
            list.add(new ElearningResource("Web Technologies", "HTML5, CSS3 and JavaScript essentials.", "https://developer.mozilla.org/en-US/docs/Web/Tutorials"));
        } else if ("Assignments".equals(type)) {
            list.add(new ElearningResource("Android App Project", "Submit your StudentHub Pro final source code here.", "https://classroom.google.com"));
            list.add(new ElearningResource("Java Quiz 2", "Covers Exception handling and Collections.", "https://forms.gle/test"));
            list.add(new ElearningResource("Database SQL Lab", "Write complex queries for the University schema.", "https://classroom.google.com"));
        } else if ("Learning Videos".equals(type)) {
            list.add(new ElearningResource("Java Programming for Beginners", "FreeCodeCamp - Full Course (10 hours).", "https://www.youtube.com/watch?v=grEKMHGYyao"));
            list.add(new ElearningResource("Android Material Design 3", "Official Google guide to modern UI/UX.", "https://www.youtube.com/watch?v=Z6XniznZ7f8"));
            list.add(new ElearningResource("SQLite in Android Studio", "Step-by-step guide to DatabaseHelper.", "https://www.youtube.com/watch?v=hDSVInZ2JCs"));
        }
    }
}