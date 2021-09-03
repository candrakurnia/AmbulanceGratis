package com.apps.ambulancegratis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.apps.ambulancegratis.utils.SessionManager;

public class SplashScreen extends AppCompatActivity {

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sessionManager = new SessionManager(this);

        final Boolean isLogin = sessionManager.getStatusLogin(this);
        Log.e("IS LOGIN", isLogin.toString());


        new Handler().postDelayed(() -> {
            if (isLogin) {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));

            } else {
                startActivity(new Intent(SplashScreen.this, GetStarted.class));
            }
            finish();
        }, 2000);

    }
}