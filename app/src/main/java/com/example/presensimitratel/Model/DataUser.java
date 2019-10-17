package com.example.presensimitratel.Model;

import com.google.gson.annotations.SerializedName;

public class DataUser {
    @SerializedName("nik")
    private String nik;
    @SerializedName("nik_tg")
    private String nik_tg;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("band")
    private String band;
    @SerializedName("unit")
    private String unit;
    @SerializedName("posisi")
    private String posisi;
    @SerializedName("area")
    private String area;
    @SerializedName("kota")
    private String kota;
    @SerializedName("alamat")
    private String alamat;
    @SerializedName("timezone")
    private String timezone;
    @SerializedName("privilege")
    private String privilege;
    @SerializedName("manager_code")
    private String manager_code;
    @SerializedName("vp_code")
    private String vp_code;
    @SerializedName("evp_code")
    private String evp_code;

    public DataUser(String nik, String nik_tg, String name, String email, String band, String unit, String posisi, String area, String kota, String alamat,
                    String timezone, String privilege, String manager_code, String vp_code, String evp_code){
        this.nik = nik;
        this.nik_tg = nik_tg;
        this.name = name;
        this.email = email;
        this.band = band;
        this.unit = unit;
        this.posisi = posisi;
        this.area = area;
        this.kota = kota;
        this.alamat = alamat;
        this.timezone = timezone;
        this.privilege = privilege;
        this.manager_code = manager_code;
        this.vp_code = vp_code;
        this.evp_code = evp_code;
    }

    public String getNIK() {
        return nik;
    }

    public void setNIK(String nik) {
        this.nik = nik;
    }

    public String getNIKTG() {
        return nik_tg;
    }

    public void setNIKTG(String nik_tg) {
        this.nik_tg = nik_tg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band= band;
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
        this.posisi = posisi;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public String getManagerCode() {
        return manager_code;
    }

    public void setManagerCode(String manager_code) {
        this.manager_code = manager_code;
    }

    public String getVPCode() {
        return vp_code;
    }

    public void setVPCode(String vp_code) {
        this.vp_code = vp_code;
    }

    public String getEVPCode() {
        return evp_code;
    }

    public void setEVPCode(String evp_code) {
        this.evp_code = evp_code;
    }
}
