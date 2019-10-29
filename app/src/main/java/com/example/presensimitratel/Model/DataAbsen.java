package com.example.presensimitratel.Model;

import com.google.gson.annotations.SerializedName;

public class DataAbsen {
    @SerializedName("id")
    private String id;
    @SerializedName("nik")
    private String nik;
    @SerializedName("nik_tg")
    private String nik_tg;
    @SerializedName("name")
    private String name;
    @SerializedName("posisi")
    private String posisi;
    @SerializedName("unit")
    private String unit;
    @SerializedName("datetime_in")
    private String datetime_in;
    @SerializedName("datetime_out")
    private String datetime_out;
    @SerializedName("keterangan_in")
    private String keterangan_in;
    @SerializedName("keterangan_out")
    private String keterangan_out;
    @SerializedName("catatan_in")
    private String catatan_in;
    @SerializedName("catatan_out")
    private String catatan_out;
    @SerializedName("ip_in")
    private String ip_in;
    @SerializedName("ip_out")
    private String ip_out;
    @SerializedName("latitude_in")
    private String latitude_in;
    @SerializedName("latitude_out")
    private String latitude_out;
    @SerializedName("longitude_in")
    private String longitude_in;
    @SerializedName("longitude_out")
    private String longitude_out;
    @SerializedName("emoji_in")
    private String emoji_in;
    @SerializedName("emoji_out")
    private String emoji_out;
    @SerializedName("manager_code")
    private String manager_code;
    @SerializedName("vp_code")
    private String vp_code;
    @SerializedName("evp_code")
    private String evp_code;
    @SerializedName("distance_in")
    private String distance_in;
    @SerializedName("distance_out")
    private String distance_out;

    public DataAbsen(String id, String nik, String nik_tg, String name, String posisi, String unit,
                    String datetime_in, String datetime_out, String keterangan_in, String keterangan_out,
                     String catatan_in, String catatan_out, String ip_in, String ip_out,
                     String latitude_in, String latitude_out, String longitude_in, String longitude_out,
                     String emoji_in, String emoji_out, String manager_code, String vp_code, String evp_code,
                     String distance_in, String distance_out){
        this.id = id;
        this.nik = nik;
        this.nik_tg = nik_tg;
        this.name = name;
        this.posisi = posisi;
        this.unit = unit;
        this.datetime_in = datetime_in;
        this.datetime_out = datetime_out;
        this.keterangan_in = keterangan_in;
        this.keterangan_out = keterangan_out;
        this.catatan_in = catatan_in;
        this.catatan_out = catatan_out;
        this.ip_in = ip_in;
        this.ip_out = ip_out;
        this.latitude_in = latitude_in;
        this.latitude_out = latitude_out;
        this.longitude_in = longitude_in;
        this.longitude_out = longitude_out;
        this.emoji_in = emoji_in;
        this.emoji_out = emoji_out;
        this.manager_code = manager_code;
        this.vp_code = vp_code;
        this.evp_code = evp_code;
        this.distance_in = distance_in;
        this.distance_out = distance_out;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
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

    public String getPosisi() {
        return posisi;
    }

    public void setPosisi(String posisi) {
        this.posisi = posisi;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDatetimeIn() {
        return datetime_in;
    }

    public void setDatetimeIn(String datetime_in) {
        this.datetime_in= datetime_in;
    }

    public String getDatetimeOut() {
        return datetime_out;
    }

    public void setDatetimeOut(String datetime_out) {
        this.datetime_out= datetime_out;
    }

    public String getKeteranganIn() {
        return keterangan_in;
    }

    public void setKeteranganIn(String keterangan_in) {
        this.keterangan_in= keterangan_in;
    }

    public String getKeteranganOut() {
        return keterangan_out;
    }

    public void setKeteranganOut(String keterangan_out) {
        this.keterangan_out= keterangan_out;
    }

    public String getCatatanIn() {
        return catatan_in;
    }

    public void setCatatanIn(String catatan_in) {
        this.catatan_in= catatan_in;
    }

    public String getCatatanOut() {
        return catatan_out;
    }

    public void setCatatanOut(String catatan_out) {
        this.catatan_out= catatan_out;
    }

    public String getIPIn() {
        return ip_in;
    }

    public void setIPIn(String ip_in) {
        this.ip_in= ip_in;
    }

    public String getIPOut() {
        return ip_out;
    }

    public void setIPOut(String ip_out) {
        this.ip_out= ip_out;
    }

    public String getLatitudeIn() {
        return latitude_in;
    }

    public void setLatitudeIn(String latitude_in) {
        this.latitude_in= latitude_in;
    }

    public String getLatitudeOut() {
        return latitude_out;
    }

    public void setLatitudeOut(String latitude_out) {
        this.latitude_out= latitude_out;
    }

    public String getLongitudeIn() {
        return longitude_in;
    }

    public void setLongitudeIn(String longitude_in) {
        this.longitude_in= longitude_in;
    }

    public String getLongitudeOut() {
        return longitude_out;
    }

    public void setLongitudeOut(String longitude_out) {
        this.longitude_out= longitude_out;
    }

    public String getEmojiIn() {
        return emoji_in;
    }

    public void setEmojiIn(String emoji_in) {
        this.emoji_in= emoji_in;
    }

    public String getEmojiOut() {
        return emoji_out;
    }

    public void setEmojiOut(String emoji_out) {
        this.emoji_out= emoji_out;
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

    public String getDistanceIn() {
        return distance_in;
    }

    public void setDistanceIn(String distance_in) {
        this.distance_in= distance_in;
    }

    public String getDistanceOut() {
        return distance_out;
    }

    public void setDistanceOut(String distance_out) {
        this.distance_out= distance_out;
    }
}
