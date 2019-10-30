package com.example.presensimitratel.Model;

public class DataMonitoring {
    private String nik, name, datetime_in, distance_in;

    public DataMonitoring() {
    }

    public DataMonitoring(String nik, String name, String datetime_in, String distance_in) {
        this.nik = nik;
        this.name = name;
        this.datetime_in = datetime_in;
        this.distance_in = distance_in;
    }

    public String getNIK() {
        return nik;
    }

    public void setNIK(String nik) {
        this.nik = nik;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatetimeIn() {
        return datetime_in;
    }

    public void setDatetimeIn(String datetime_in) {
        this.datetime_in = datetime_in;
    }

    public String getDistanceIn() {
        return distance_in;
    }

    public void setDistanceIn(String distance_in) {
        this.distance_in = distance_in;
    }
}
