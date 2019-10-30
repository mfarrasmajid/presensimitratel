package com.example.presensimitratel.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetUlangTahun {
    @SerializedName("status")
    Boolean status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    List<DataUlangTahun> data;

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

    public List<DataUlangTahun> getListDataUlangTahun() {
        return data;
    }
    public void setListDataUlangTahun (List<DataUlangTahun> data) {
        this.data = data;
    }
}
