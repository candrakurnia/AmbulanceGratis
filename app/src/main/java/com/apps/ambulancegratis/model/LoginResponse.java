package com.apps.ambulancegratis.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("error")
    private Boolean error;
    @SerializedName("user")
    private User user;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
