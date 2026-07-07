package com.example.studenthub.models;

public class CampusBuilding {

    private int id;
    private String buildingName;
    private String latitude;
    private String longitude;
    private String date;
    private String time;

    public CampusBuilding(int id,
                          String buildingName,
                          String latitude,
                          String longitude,
                          String date,
                          String time) {

        this.id = id;
        this.buildingName = buildingName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
