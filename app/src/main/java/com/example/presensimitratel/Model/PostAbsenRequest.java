package com.example.presensimitratel.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostAbsenRequest {
    @SerializedName("status")
    Boolean status;
    @SerializedName("message")
    String message;
    @SerializedName("nik_tg")
    List<DataAbsen> data;

    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean status){
        this.status = status;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataAbsen> getListDataAbsen() {
        return data;
    }
    public void setListDataAbsen(List<DataAbsen> data) {
        this.data= data;
    }
}
