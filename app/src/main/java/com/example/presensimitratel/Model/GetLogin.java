package com.example.presensimitratel.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetLogin {
    @SerializedName("status")
    Boolean status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    List<DataUser> data;

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

    public List<DataUser> getListDataUser() {
        return data;
    }
    public void setListDataUser(List<DataUser> data) {
        this.data = data;
    }


}

