package com.apps.ambulancegratis.rest;

import com.apps.ambulancegratis.model.DriverResponse;
import com.apps.ambulancegratis.model.LoginResponse;
import com.apps.ambulancegratis.model.PesananResponse;
import com.apps.ambulancegratis.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("loginuser.php")
    Call<LoginResponse> loginRequest(@Field("fullname") String fullname,
                                     @Field("password") String password);

    @FormUrlEncoded
    @POST("registeruser.php")
    Call<RegisterResponse> registerRequest(@Field("fullname") String fullname,
                                           @Field("password") String password,
                                           @Field("no_telpon") String no_telpon,
                                           @Field("no_kk") String no_kk,
                                           @Field("no_ktp") String no_ktp);

    @FormUrlEncoded
    @POST("post_ambulance.php")
    Call<PesananResponse> pesananRequest(@Field("id_user") String id_user,
                                         @Field("alamat") String alamat);

    @GET("get_driver.php")
    Call<DriverResponse> driverRequest();

}
