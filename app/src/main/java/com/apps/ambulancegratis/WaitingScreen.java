package com.apps.ambulancegratis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.apps.ambulancegratis.helpers.FirebaseHelper;

public class WaitingScreen extends AppCompatActivity {

    TextView back;
    private FirebaseHelper firebaseHelper = new FirebaseHelper("0000");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_screen);

        back = findViewById(R.id.textView5);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o = new Intent(WaitingScreen.this, MainActivity.class);
                startActivity(o);
                finish();
            }
        });
    }
/*
    @Override
    public void onBackPressed() {
        Intent back = new Intent(this,MainActivity.class);
        startActivity(back);
        finish();
    }*/
}