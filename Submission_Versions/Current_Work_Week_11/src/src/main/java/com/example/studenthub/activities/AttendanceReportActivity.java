package com.example.studenthub.activities;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studenthub.R;
import com.example.studenthub.adapters.AttendanceAdapter;
import com.example.studenthub.database.DatabaseHelper;
import com.example.studenthub.models.Attendance;

import java.util.ArrayList;

public class AttendanceReportActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Attendance> attendanceList;
    AttendanceAdapter adapter;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_report);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Attendance Records");
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerAttendance);

        attendanceList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(this);

        loadAttendance();

        adapter = new AttendanceAdapter(attendanceList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadAttendance() {
        Cursor cursor = databaseHelper.getAllAttendance();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String studentName = cursor.getString(0);
                if (studentName == null) {
                    studentName = "Unregistered (ID: " + cursor.getInt(3) + ")";
                }
                
                attendanceList.add(new Attendance(
                        cursor.getInt(3),    // studentId
                        studentName,
                        cursor.getString(1), // date
                        cursor.getString(2)  // status
                ));
            }
            cursor.close();
        }
    }
}