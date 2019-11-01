package com.example.presensimitratel.Model;

public class DataStatus {
    private String date_long, note, color, clickable;

    public DataStatus() {
    }

    public DataStatus(String date_long, String note, String color, String clickable) {
        this.date_long = date_long;
        this.note = note;
        this.color = color;
        this.clickable = clickable;
    }

    public String getDateLong() {
        return date_long;
    }

    public void setDateLong(String date_long) {
        this.date_long = date_long;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getColor1() {
        return color;
    }

    public void setColor1(String color) {
        this.color= color;
    }

    public String getClickable() {
        return clickable;
    }

    public void setClickable(String clickable) {
        this.clickable= clickable;
    }
}
