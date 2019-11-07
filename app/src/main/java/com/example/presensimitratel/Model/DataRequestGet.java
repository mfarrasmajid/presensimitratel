package com.example.presensimitratel.Model;

public class DataRequestGet {
    private String date, ket;
    private double lat, lon;

    public DataRequestGet() {
    }

    public DataRequestGet(String date, String ket, double lat, double lon) {
        this.date = date;
        this.ket = ket;
        this.lat = lat;
        this.lon = lon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
