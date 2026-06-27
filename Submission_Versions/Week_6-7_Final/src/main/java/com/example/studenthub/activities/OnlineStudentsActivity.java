package com.example.studenthub.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.studenthub.R;
import com.example.studenthub.adapters.OnlineStudentAdapter;
import com.example.studenthub.models.OnlineStudent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OnlineStudentsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView tvEmpty;
    OnlineStudentAdapter adapter;
    List<OnlineStudent> studentList;

    String url = "https://jsonplaceholder.typicode.com/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_students);

        recyclerView = findViewById(R.id.recyclerViewOnline);
        progressBar = findViewById(R.id.progressBarOnline);
        tvEmpty = findViewById(R.id.tvEmptyOnline);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentList = new ArrayList<>();
        adapter = new OnlineStudentAdapter(studentList);
        recyclerView.setAdapter(adapter);

        fetchOnlineStudents();
        
        findViewById(R.id.toolbar).setOnClickListener(v -> finish());
    }

    private void fetchOnlineStudents() {
        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            String name = obj.getString("name");
                            String username = obj.getString("username");
                            studentList.add(new OnlineStudent(name, username));
                        }

                        progressBar.setVisibility(View.GONE);
                        if (studentList.isEmpty()) {
                            tvEmpty.setVisibility(View.VISIBLE);
                        } else {
                            adapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, "Data Parsing Error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show();
                }
        );

        Volley.newRequestQueue(this).add(request);
    }
}