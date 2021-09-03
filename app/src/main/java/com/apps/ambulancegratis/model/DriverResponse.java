package com.apps.ambulancegratis.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DriverResponse {

    @SerializedName("value")
    String value;
    @SerializedName("result")
    List<Driver> driver;

    public String getValue() {
        return value;
    }

    public List<Driver> getDriver() {
        return driver;
    }
}
