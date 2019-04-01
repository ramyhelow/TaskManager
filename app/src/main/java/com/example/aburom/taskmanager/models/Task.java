package com.example.aburom.taskmanager.models;

import java.io.Serializable;

public class Task implements Serializable {

//    public static final String DB_NAME = "tasks_db";
//    public static final String TABLE_NAME = "tasks";
//
//    public static final String COL_ID = "id";
//    public static final String COL_TITLE = "title";
//    public static final String COL_SUMMARY = "summary";
//    public static final String COL_DESCRIPTION = "description";
//    public static final String COL_DATE = "date";
//    public static final String COL_IMAGEURL = "image_url";
//    public static final String COL_LONGITUDE = "longitude";
//    public static final String COL_LATITUDE = "latitude";
//
//    public static final String CREATE_TABLE =
//            "CREATE TABLE " + TABLE_NAME + " ( "
//                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                    + COL_TITLE + " TEXT, "
//                    + COL_DATE + " TEXT, "
//                    + COL_SUMMARY + " TEXT, "
//                    + COL_DESCRIPTION + " TEXT, "
//                    + COL_IMAGEURL + " TEXT, "
//                    + COL_LONGITUDE + " DOUBLE, "
//                    + COL_LATITUDE + " DOUBLE)";
//
//    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
//
//    public static final String GET_TASKS = "SELECT * FROM " + TABLE_NAME + " ORDER BY ID DESC";



    private int id;
    private String title;
    private String summary;
    private String description;
    private String time;
    private String date;
    private String imageUrl;
    //private byte[] imageByte;
    private Double longitude;
    private Double latitude;

    public Task(int id, String title, String summary, String description, String time, String date, String imageUrl, Double longitude, Double latitude) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.description = description;
        this.time = time;
        this.date = date;
        this.imageUrl = imageUrl;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Task() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}

