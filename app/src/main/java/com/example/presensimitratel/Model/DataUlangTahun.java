package com.example.presensimitratel.Model;

public class DataUlangTahun {
    private String nik, name, unit, posisi;

    public DataUlangTahun() {
    }

    public DataUlangTahun(String nik, String name, String unit, String posisi) {
        this.nik = nik;
        this.name = name;
        this.unit= unit;
        this.posisi = posisi;
    }

    public String getNIK() {
        return nik;
    }

    public void setNIK(String nik) {
        this.nik= nik;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi= posisi;
    }
}
