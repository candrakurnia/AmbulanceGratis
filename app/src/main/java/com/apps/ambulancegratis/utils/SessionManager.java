package com.apps.ambulancegratis.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.apps.ambulancegratis.Login;
import com.apps.ambulancegratis.MainActivity;
import com.apps.ambulancegratis.model.User;
import com.google.gson.Gson;


public class SessionManager {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    public static final String KEY_PROFILE_USER = "key.user";
    private static final String is_login = "loginstatus";
    private final String SHARE_NAME = "loginsession";
    public static final String SP_SUDAH_LOGIN = "spSudahLogin";
    private Context _context;

    public SessionManager(Context context) {
        this._context = context;
        sp = _context.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

    }

    private static SharedPreferences getSharedPreferences(Context _context) {
        return PreferenceManager.getDefaultSharedPreferences(_context);
    }

    public User getUser(Context _context) {
        String json = getSharedPreferences(_context).getString(KEY_PROFILE_USER, null);
        if (TextUtils.isEmpty(json)) return null;
        return new Gson().fromJson(json, User.class);
    }

    public void saveUser(Context _context, User user) {
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        getSharedPreferences(_context).edit().putString(KEY_PROFILE_USER, userJson).apply();
    }


    public Boolean getSPSudahLogin(){
        return sp.getBoolean(is_login, false);
    }


    public Boolean getStatusLogin(Context context) {
        return getSharedPreferences(context).getBoolean(is_login, false);
    }
    public void storeLogin(Boolean status) {
        getSharedPreferences(_context).edit().putBoolean(is_login, status).apply();
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}



