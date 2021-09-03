package com.apps.ambulancegratis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.ambulancegratis.model.User;
import com.apps.ambulancegratis.utils.SessionManager;

import java.util.concurrent.atomic.AtomicBoolean;

public class Profil extends AppCompatActivity {

    TextView nlengkap, telp, kk, ktp, bck;
    SessionManager sessionManager;
    Button keluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        sessionManager = new SessionManager(this);
        User user = sessionManager.getUser(this);

        nlengkap = findViewById(R.id.tv_lengkap);
        telp = findViewById(R.id.tv_notelp);
        kk = findViewById(R.id.tv_no_kk);
        ktp = findViewById(R.id.tv_no_ktp);
        bck = findViewById(R.id.tv_back);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(Profil.this, MainActivity.class);
                startActivity(b);
                finish();
            }
        });


        if (user != null) {
            nlengkap.setText(user.getFullname());
            telp.setText(user.getNo_telpon());
            kk.setText(user.getNo_kk());
            ktp.setText(user.getNo_ktp());
        }

        keluar = findViewById(R.id.btn_logout);
        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
                sessionManager.storeLogin(false);
                Toast.makeText(Profil.this, "Anda Berhasil Logout", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Profil.this, GetStarted.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent c = new Intent(Profil.this, MainActivity.class);
        startActivity(c);
        finish();
    }
}