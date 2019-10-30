package com.example.presensimitratel.Rest;

import com.example.presensimitratel.Model.GetAbsenData;
import com.example.presensimitratel.Model.GetLogin;
import com.example.presensimitratel.Model.GetMonitoring;
import com.example.presensimitratel.Model.GetUlangTahun;
import com.example.presensimitratel.Model.PostLogin;

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

}
