package com.apps.ambulancegratis.model;

public class LokasiUser {
    private double lat;
    private double lng;
    private String id_user;

    public LokasiUser(String id_user, double lat, double lng) {
        this.id_user = id_user;
        this.lat = lat;
        this.lng = lng;
    }

    public void updateUser(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "LokasiUser{" +
                "id_user='" + id_user + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
