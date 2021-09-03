package com.apps.ambulancegratis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.ambulancegratis.model.LoginResponse;
import com.apps.ambulancegratis.model.User;
import com.apps.ambulancegratis.rest.Api;
import com.apps.ambulancegratis.rest.ApiInterface;
import com.apps.ambulancegratis.utils.SessionManager;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private TextInputEditText nama, pass;
    ApiInterface apiInterface;
    ProgressDialog loading;
    TextView regist;
    Button login;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new SessionManager(Login.this);
        apiInterface = Api.getClient().create(ApiInterface.class);
        nama = findViewById(R.id.edt_username);
        pass = findViewById(R.id.edt_password);
        login = findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent go = new Intent(Login.this,MainActivity.class);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(login, "logo_btn");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
                startActivity(go,options.toBundle());
                finish();*/
                String name = nama.getText().toString();
                String pw = pass.getText().toString();
                try {
                    if (name.length() < 1) {
                        Toast.makeText(Login.this, "Username tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    } else if (pw.length() < 1) {
                        Toast.makeText(Login.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    } else {
                        submitLogin(name, pw);
                    }
                } catch (Exception e) {
                    Log.e("Error", "Error" + e.getLocalizedMessage());
                }

            }
        });

        regist = findViewById(R.id.tv_register);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fg = new Intent(Login.this, Register.class);
                startActivity(fg);
                finish();
            }
        });

    }

    private void submitLogin(String username, String password) {
        loading = ProgressDialog.show(Login.this, null, "Harap Tunggu...", true, false);
        apiInterface.loginRequest(username, password).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (!(response.body().getError())) {
                            User user = response.body().getUser();
                            sessionManager.saveUser(Login.this, user);
                            sessionManager.storeLogin(true);
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
                            loading.dismiss();
                        } else {
                            handlingError("Gagal Login 1 !");
                        }
                    } else {
                        handlingError("Gagal Login 2 !");
                    }
                } else {
                    handlingError("Gagal Login 3 !");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("ERROR LOGIN", "Error : " + t.getLocalizedMessage());
                handlingError("No Connection !");
            }
        });
    }

    private void handlingError(String message) {
        Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
        loading.dismiss();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            moveTaskToBack(true);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan Sekali Lagi Untuk Keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}