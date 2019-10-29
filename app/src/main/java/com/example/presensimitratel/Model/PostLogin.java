package com.example.presensimitratel.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostLogin {
    @SerializedName("status")
    Boolean status;
    @SerializedName("message")
    String message;
    @SerializedName("ldap_message")
    String ldap_message;
    @SerializedName("nik_tg")
    String nik_tg;

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

    public String getLDAPMessage() {
        return ldap_message;
    }
    public void setLDAPMessage(String ldap_message) {
        this.ldap_message = ldap_message;
    }

    public String getNIKTG() {
        return nik_tg;
    }
    public void setNIKTG(String nik_tg) {
        this.nik_tg = nik_tg;
    }


}