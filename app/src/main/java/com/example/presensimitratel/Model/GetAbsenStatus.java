package com.example.presensimitratel.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAbsenStatus {
    @SerializedName("status")
    Boolean status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    List<DataStatus> data;

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

    public List<DataStatus> getListDataStatus() {
        return data;
    }
    public void setListDataStatus (List<DataStatus> data) {
        this.data = data;
    }

}
