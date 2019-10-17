package com.example.presensimitratel.Rest;

import com.example.presensimitratel.Model.GetLogin;
import com.example.presensimitratel.Model.PostLogin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("api/login")
    Call<GetLogin> getLogin();
    @FormUrlEncoded
    @POST("api/login")
    Call<PostLogin> postLogin(@Field("username") String username,
                              @Field("password") String password);

}
