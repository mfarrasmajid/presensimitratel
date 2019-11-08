package com.example.presensimitratel;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    public static final String SP_PRESENSI_MITRATEL = "spPresensiMitratel";

    public static final String SP_NIK = "spNIK";
    public static final String SP_NIK_TG = "spNIKTG";
    public static final String SP_NAME = "spName";
    public static final String SP_EMAIL = "spEmail";
    public static final String SP_BAND = "spBand";
    public static final String SP_UNIT = "spUnit";
    public static final String SP_POSISI = "spPosisi";
    public static final String SP_AREA = "spArea";
    public static final String SP_KOTA = "spKota";
    public static final String SP_ALAMAT = "spAlamat";
    public static final String SP_TIMEZONE = "spTimezone";
    public static final String SP_PRIVILEGE = "spPrivilege";
    public static final String SP_MANAGER_CODE = "spManagerCode";
    public static final String SP_VP_CODE = "spVPCode";
    public static final String SP_EVP_CODE = "spEVPCode";

    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    public static final String SP_KET_ABSEN = "spKetAbsen";
    public static final String SP_DATE_ABSEN = "spDateAbsen";
    public static final String SP_MASUK_PULANG = "spMasukPulang";

    public static final String SP_LAT_KANTOR = "spLatKantor";
    public static final String SP_LONG_KANTOR = "spLongKantor";
    public static final String SP_LAT = "spLat";
    public static final String SP_LONG = "spLong";

    public static final String SP_DISTANCE = "spDistance";



    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences(SP_PRESENSI_MITRATEL, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public String getSPNIK(){
        return sp.getString(SP_NIK, "");
    }
    public String getSPNIKTG(){
        return sp.getString(SP_NIK_TG, "");
    }

    public String getSPName(){
        return sp.getString(SP_NAME, "");
    }

    public String getSPEmail(){
        return sp.getString(SP_EMAIL, "");
    }

    public String getSPBand(){
        return sp.getString(SP_BAND, "");
    }

    public String getSPUnit(){
        return sp.getString(SP_UNIT, "");
    }

    public String getSPPosisi(){
        return sp.getString(SP_POSISI, "");
    }

    public String getSPArea(){
        return sp.getString(SP_AREA, "");
    }

    public String getSPKota(){
        return sp.getString(SP_KOTA, "");
    }

    public String getSPAlamat(){
        return sp.getString(SP_ALAMAT, "");
    }

    public String getSPTimezone(){
        return sp.getString(SP_TIMEZONE, "");
    }

    public String getSPPrivilege(){
        return sp.getString(SP_PRIVILEGE, "");
    }

    public String getSPManagerCode(){
        return sp.getString(SP_MANAGER_CODE, "");
    }

    public String getSPVPCode(){
        return sp.getString(SP_VP_CODE, "");
    }

    public String getSPEVPCode(){
        return sp.getString(SP_EVP_CODE, "");
    }

    public Boolean getSPSudahLogin(){
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }

    public String getSPKetAbsen(){
        return sp.getString(SP_KET_ABSEN, "");
    }

    public String getSPDateAbsen(){
        return sp.getString(SP_DATE_ABSEN, "");
    }

    public String getSPMasukPulang(){
        return sp.getString(SP_MASUK_PULANG, "");
    }

    public String getSPLatKantor(){
        return sp.getString(SP_LAT_KANTOR, "");
    }

    public String getSPLat(){
        return sp.getString(SP_LAT, "");
    }

    public String getSPLongKantor(){
        return sp.getString(SP_LONG_KANTOR, "");
    }

    public String getSPLong(){
        return sp.getString(SP_LONG, "");
    }

    public String getSpDistance(){
        return sp.getString(SP_DISTANCE, "");
    }

}
