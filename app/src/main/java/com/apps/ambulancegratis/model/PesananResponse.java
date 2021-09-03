package com.apps.ambulancegratis.model;

import com.google.gson.annotations.SerializedName;

public class PesananResponse {

    @SerializedName("error")
    private boolean error;
    @SerializedName("user")
    private User user;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
