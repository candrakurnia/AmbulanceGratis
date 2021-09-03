package com.apps.ambulancegratis.model;

import com.google.gson.annotations.SerializedName;

public class Driver {
    @SerializedName("fullname")
    private String fullname;
    @SerializedName("no_plat")
    private String no_plat;
    @SerializedName("no_telpon")
    private String no_telpon;

    public Driver(String fullname, String no_plat, String no_telpon) {
        this.fullname = fullname;
        this.no_plat = no_plat;
        this.no_telpon = no_telpon;
    }

    public String getFullname() {
        return fullname;
    }

    public String getNo_plat() {
        return no_plat;
    }

    public String getNo_telpon() {
        return no_telpon;
    }
}
