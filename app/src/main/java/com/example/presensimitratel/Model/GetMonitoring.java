package com.example.presensimitratel.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetMonitoring {
    @SerializedName("status")
    Boolean status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    List<DataMonitoring> data;

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

    public List<DataMonitoring> getListDataMonitoring() {
        return data;
    }
    public void setListDataMonitoring (List<DataMonitoring> data) {
        this.data = data;
    }
}
