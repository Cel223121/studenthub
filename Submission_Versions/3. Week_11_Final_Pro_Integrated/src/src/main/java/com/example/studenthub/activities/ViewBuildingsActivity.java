package com.example.studenthub.activities;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studenthub.R;
import com.example.studenthub.adapters.CampusBuildingAdapter;
import com.example.studenthub.database.DatabaseHelper;
import com.example.studenthub.models.CampusBuilding;

import java.util.ArrayList;

public class ViewBuildingsActivity extends AppCompatActivity {

    RecyclerView recyclerBuildings;

    ArrayList<CampusBuilding> buildingList;

    CampusBuildingAdapter adapter;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_buildings);

        recyclerBuildings = findViewById(R.id.recyclerBuildings);

        recyclerBuildings.setLayoutManager(
                new LinearLayoutManager(this));

        buildingList = new ArrayList<>();

        adapter = new CampusBuildingAdapter(this, buildingList);

        recyclerBuildings.setAdapter(adapter);

        databaseHelper = new DatabaseHelper(this);

        loadBuildings();
    }

    private void loadBuildings() {

        Cursor cursor = databaseHelper.getAllBuildings();

        buildingList.clear();

        while (cursor.moveToNext()) {

            CampusBuilding building = new CampusBuilding(

                    cursor.getInt(0), // buildingId

                    cursor.getString(1), // buildingName

                    cursor.getString(3), // latitude

                    cursor.getString(4), // longitude

                    cursor.getString(5), // date

                    cursor.getString(6)  // time

            );

            buildingList.add(building);

        }

        cursor.close();

        adapter.notifyDataSetChanged();

    }

}