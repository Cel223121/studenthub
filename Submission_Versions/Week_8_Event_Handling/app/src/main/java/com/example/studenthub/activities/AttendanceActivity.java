package com.example.studenthub.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studenthub.R;
import com.example.studenthub.database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class AttendanceActivity extends AppCompatActivity {

    AutoCompleteTextView autoStudent;
    EditText etDate;
    Button btnPresent, btnAbsent;
    DatabaseHelper databaseHelper;

    ArrayList<String> studentNames;
    HashMap<String, Integer> studentMap; // Maps Name -> ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        autoStudent = findViewById(R.id.autoCompleteStudent);
        etDate = findViewById(R.id.etDate);
        btnPresent = findViewById(R.id.btnPresent);
        btnAbsent = findViewById(R.id.btnAbsent);

        databaseHelper = new DatabaseHelper(this);
        
        loadStudents();

        String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        etDate.setText(currentDate);

        btnPresent.setOnClickListener(v -> saveAttendance("Present"));
        btnAbsent.setOnClickListener(v -> saveAttendance("Absent"));

        findViewById(R.id.btnViewReport).setOnClickListener(v -> {
            startActivity(new Intent(this, AttendanceReportActivity.class));
        });
        
        findViewById(R.id.toolbar).setOnClickListener(v -> finish());
    }

    private void loadStudents() {
        studentNames = new ArrayList<>();
        studentMap = new HashMap<>();

        Cursor cursor = databaseHelper.getAllStudents();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                
                String displayName = name + " (ID: " + id + ")";
                studentNames.add(displayName);
                studentMap.put(displayName, id);
            }
            cursor.close();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, studentNames);
        autoStudent.setAdapter(adapter);
    }

    private void saveAttendance(String status){
        String selectedName = autoStudent.getText().toString().trim();
        String date = etDate.getText().toString().trim();

        if(selectedName.isEmpty() || !studentMap.containsKey(selectedName)){
            Toast.makeText(this, "Please select a valid student from the list", Toast.LENGTH_SHORT).show();
            return;
        }

        Integer studentId = studentMap.get(selectedName);
        if (studentId != null) {
            boolean result = databaseHelper.saveAttendance(studentId, date, status);
            if(result){
                Toast.makeText(this, "Attendance Saved for " + selectedName, Toast.LENGTH_SHORT).show();
                autoStudent.setText(""); // Clear for next
            } else {
                Toast.makeText(this, "Error saving record", Toast.LENGTH_SHORT).show();
            }
        }
    }
}