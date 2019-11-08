package com.example.presensimitratel.Rest;

import com.example.presensimitratel.Model.GetAbsenData;
import com.example.presensimitratel.Model.GetAbsenRequest;
import com.example.presensimitratel.Model.GetAbsenStatus;
import com.example.presensimitratel.Model.GetLogin;
import com.example.presensimitratel.Model.GetMonitoring;
import com.example.presensimitratel.Model.GetUlangTahun;
import com.example.presensimitratel.Model.PostAbsenRequest;
import com.example.presensimitratel.Model.PostLogin;

import org.json.JSONArray;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ApiInterface {
    @GET("api/login")
    Call<GetLogin> getLogin(@QueryMap HashMap<String, String> params);
    @FormUrlEncoded
    @POST("api/login")
    Call<PostLogin> postLogin(@Field("username") String username,
                              @Field("password") String password);
    @GET("api/absendata")
    Call<GetAbsenData> getAbsenData(@QueryMap HashMap<String, String> params);
    @GET("api/monitoring")
    Call<GetMonitoring> getMonitoring(@QueryMap HashMap<String, String> params);
    @GET("api/ulangtahun")
    Call<GetUlangTahun> getUlangTahun(@QueryMap HashMap<String, String> params);
    @GET("api/absenstatus")
    Call<GetAbsenStatus> getAbsenStatus(@QueryMap HashMap<String, String> params);
    @GET("api/absenrequest")
    Call<GetAbsenRequest> getAbsenRequest(@QueryMap HashMap<String, String> params);
    @FormUrlEncoded
    @POST("api/absenrequest")
    Call<PostAbsenRequest> postAbsenRequest(@Field("nik") String nik,
                                            @Field("nik_tg") String nik_tg,
                                            @Field("name") String name,
                                            @Field("posisi") String posisi,
                                            @Field("unit") String unit,
                                            @Field("date") String date,
                                            @Field("ket") String ket,
                                            @Field("cat") String cat,
                                            @Field("ip") String ip,
                                            @Field("lat") String lat,
                                            @Field("lon") String lon,
                                            @Field("emoji") String emoji,
                                            @Field("manager_code") String manager_code,
                                            @Field("vp_code") String vp_code,
                                            @Field("evp_code") String evp_code,
                                            @Field("distance") String distance,
                                            @Field("status") String status
                                            );
}
