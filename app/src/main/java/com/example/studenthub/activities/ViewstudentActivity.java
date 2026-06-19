package com.example.studenthub.activities;

import com.example.studenthub.R;
import com.example.studenthub.database.DatabaseHelper;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studenthub.adapters.StudentAdapter;
import com.example.studenthub.models.Student;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ViewstudentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Student> list;
    DatabaseHelper db;
    StudentAdapter adapter;
    FloatingActionButton fab;
    EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        recyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.fabAddStudent);
        etSearch = findViewById(R.id.etSearch);

        db = new DatabaseHelper(this);
        list = new ArrayList<>();

        loadStudents();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentAdapter(list);
        recyclerView.setAdapter(adapter);

        fab.setOnClickListener(v -> {
            startActivity(new Intent(this, AddStudentActivity.class));
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        
        findViewById(R.id.toolbar).setOnClickListener(v -> finish());
    }

    private void filter(String text) {
        ArrayList<Student> filteredList = new ArrayList<>();
        for (Student item : list) {
            if (item.getName().toLowerCase().contains(text.toLowerCase()) || 
                item.getCourse().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadStudents();
        adapter.notifyDataSetChanged();
    }

    private void loadStudents() {
        list.clear();
        Cursor cursor = db.getAllStudents();

        if (cursor.getCount() == 0) {
            cursor.close();
            return;
        }

        while (cursor.moveToNext()) {
            list.add(new Student(
                    cursor.getString(0), // id
                    cursor.getString(1), // name
                    cursor.getString(2), // email
                    cursor.getString(3), // course
                    cursor.getInt(4),    // year
                    cursor.getString(5)  // phone
            ));
        }

        cursor.close();
    }
}