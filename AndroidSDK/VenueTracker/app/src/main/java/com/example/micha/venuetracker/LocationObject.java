package com.example.micha.venuetracker;

/**
 * Created by micha on 13.01.2018.
 */

public class LocationObject {
    private String name;
    private String desc;
    private float rad;
    private double loong;
    private double lat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public float getRad() {
        return rad;
    }

    public void setRad(float rad) {
        this.rad = rad;
    }

    public double getLoong() {
        return loong;
    }

    public void setLoong(double loong) {
        this.loong = loong;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }


    LocationObject()
    {
        this.name = "Warszawa";
        this.desc = "Miasto";
        this.rad = 20;
        this.lat = -50;
        this.loong = -50;
    }
    LocationObject(String name, String desc, float rad, double loong, double lat){
        this.name = name;
        this.desc = desc;
        this.rad = rad;
        this.loong = loong;
        this.lat = lat;
    }

}
