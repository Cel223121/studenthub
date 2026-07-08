package com.example.studenthub.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.studenthub.R;
import com.example.studenthub.database.DatabaseHelper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CampusExplorerActivity extends AppCompatActivity {

    EditText etBuildingName;

    ImageView imgBuilding;

    Button btnCapture, btnLocation, btnSave, btnViewBuildings;

    TextView txtLatitude, txtLongitude, txtDate, txtTime;

    Bitmap capturedBitmap;
    DatabaseHelper databaseHelper;

    private static final int CAMERA_PERMISSION = 200;
    private static final int LOCATION_PERMISSION = 201;

    ActivityResultLauncher<Intent> cameraLauncher;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_explorer);

        etBuildingName = findViewById(R.id.etBuildingName);

        imgBuilding = findViewById(R.id.imgBuilding);

        btnCapture = findViewById(R.id.btnCapture);
        btnLocation = findViewById(R.id.btnLocation);
        btnSave = findViewById(R.id.btnSave);
        btnViewBuildings = findViewById(R.id.btnViewBuildings);

        txtLatitude = findViewById(R.id.txtLatitude);
        txtLongitude = findViewById(R.id.txtLongitude);

        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);

        databaseHelper = new DatabaseHelper(this);

        loadCurrentDateTime();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    if(result.getResultCode()==RESULT_OK
                            && result.getData()!=null){

                        Bundle bundle = result.getData().getExtras();

                        capturedBitmap =
                                (Bitmap) bundle.get("data");

                        imgBuilding.setImageBitmap(capturedBitmap);

                        Toast.makeText(
                                this,
                                "Photo Captured Successfully",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                });

        btnCapture.setOnClickListener(v -> openCamera());
        btnLocation.setOnClickListener(v -> getCurrentLocation());
        btnSave.setOnClickListener(v -> saveBuilding());
        btnViewBuildings.setOnClickListener(v -> {
            startActivity(new Intent(CampusExplorerActivity.this, ViewBuildingsActivity.class));
        });

    }

    private void saveBuilding() {

        String building =
                etBuildingName.getText().toString().trim();

        String latitude =
                txtLatitude.getText().toString();

        String longitude =
                txtLongitude.getText().toString();

        String date =
                txtDate.getText().toString();

        String time =
                txtTime.getText().toString();

        if(building.isEmpty()){
            etBuildingName.setError("Building name is required");
            return;
        }

        if(latitude.contains("Not Available") || longitude.contains("Not Available")){
            Toast.makeText(this,
                    "Retrieve GPS location first",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if(capturedBitmap == null){
            Toast.makeText(this,
                    "Capture a building photo",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // For now, imagePath is a placeholder. 
        // In a real app, we'd save the bitmap to disk and store the path.
        String imagePath = "internal_storage_placeholder";

        boolean saved =
                databaseHelper.saveBuilding(
                        building,
                        imagePath,
                        latitude,
                        longitude,
                        date,
                        time
                );

        if(saved){
            new AlertDialog.Builder(this)
                    .setTitle("Campus Explorer")
                    .setMessage("Building information saved successfully.")
                    .setPositiveButton("OK", null)
                    .show();

            clearFields();

        }else{
            new AlertDialog.Builder(this)
                    .setTitle("Save Failed")
                    .setMessage("Unable to save building.\nPlease try again.")
                    .setPositiveButton("OK", null)
                    .show();
        }

    }

    private void clearFields(){

        etBuildingName.setText("");

        txtLatitude.setText("Latitude : Not Available");

        txtLongitude.setText("Longitude : Not Available");

        loadCurrentDateTime();

        imgBuilding.setImageResource(
                R.mipmap.ic_launcher
        );

    }

    private void openCamera(){

        if(ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION);

        }else{

            Intent intent =
                    new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            cameraLauncher.launch(intent);

        }

    }

    private void getCurrentLocation() {

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION);

            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {

                    if (location != null) {

                        txtLatitude.setText(
                                "Latitude : " + location.getLatitude());

                        txtLongitude.setText(
                                "Longitude : " + location.getLongitude());

                        Toast.makeText(
                                this,
                                "Location Retrieved Successfully",
                                Toast.LENGTH_SHORT
                        ).show();

                    } else {

                        Toast.makeText(
                                this,
                                "Unable to get location. Turn on GPS.",
                                Toast.LENGTH_LONG
                        ).show();

                    }

                });

    }

    private void loadCurrentDateTime(){

        String date =
                new SimpleDateFormat(
                        "dd/MM/yyyy",
                        Locale.getDefault())
                        .format(new Date());

        String time =
                new SimpleDateFormat(
                        "HH:mm:ss",
                        Locale.getDefault())
                        .format(new Date());

        txtDate.setText("Date : "+date);

        txtTime.setText("Time : "+time);

    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults);

        if(requestCode==CAMERA_PERMISSION){

            if(grantResults.length>0 &&
                    grantResults[0]
                            ==PackageManager.PERMISSION_GRANTED){

                openCamera();

            }else{

                Toast.makeText(
                        this,
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT
                ).show();

            }

        }

        if (requestCode == LOCATION_PERMISSION) {

            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                getCurrentLocation();

            } else {

                Toast.makeText(
                        this,
                        "Location Permission Denied",
                        Toast.LENGTH_SHORT
                ).show();

            }

        }

    }

}
