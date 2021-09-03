package com.apps.ambulancegratis.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id_user")
    private String id_user;
    @SerializedName("fullname")
    private String fullname;
    @SerializedName("password")
    private String password;
    @SerializedName("no_telpon")
    private String no_telpon;
    @SerializedName("no_kk")
    private String no_kk;
    @SerializedName("no_ktp")
    private String no_ktp;

    public User(String id_user,String fullname,String password, String no_telpon, String no_kk, String no_ktp) {
        this.id_user = id_user;
        this.fullname = fullname;
        this.password = password;
        this.no_telpon = no_telpon;
        this.no_kk = no_kk;
        this.no_ktp = no_ktp;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNo_telpon() {
        return no_telpon;
    }

    public void setNo_telpon(String no_telpon) {
        this.no_telpon = no_telpon;
    }

    public String getNo_kk() {
        return no_kk;
    }

    public void setNo_kk(String no_kk) {
        this.no_kk = no_kk;
    }

    public String getNo_ktp() {
        return no_ktp;
    }

    public void setNo_ktp(String no_ktp) {
        this.no_ktp = no_ktp;
    }

}
