package com.example.studenthub.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studenthub.R;
import com.example.studenthub.adapters.AnnouncementAdapter;
import com.example.studenthub.models.Announcement;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;

    List<Announcement> list;
    AnnouncementAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new AnnouncementAdapter(list);
        recyclerView.setAdapter(adapter);

        // Load strictly English announcements
        loadAnnouncements();
        
        findViewById(R.id.toolbar).setOnClickListener(v -> finish());
    }

    private void loadAnnouncements() {
        progressBar.setVisibility(View.VISIBLE);

        // Clear previous items to ensure English only
        list.clear();

        list.add(new Announcement("Upcoming Semester Registration", 
                "Registration for the September 2026 semester begins next Monday. Ensure all fees are cleared to access the registration portal."));
        
        list.add(new Announcement("Campus Security Update", 
                "New electronic gate passes will be issued starting tomorrow at the student affairs office. Please bring your ID."));
        
        list.add(new Announcement("Career Fair 2026", 
                "Top tech companies are visiting our campus this Friday. Prepare your resumes and join us at the Great Hall."));
        
        list.add(new Announcement("Holiday Notice", 
                "The university will be closed for the public holiday on June 18th. Classes will resume as normal on the 19th."));
        
        list.add(new Announcement("Student Council Elections", 
                "Nomination forms for the next Student Council leaders are now available. Pick yours up at the Admin block."));

        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }
}