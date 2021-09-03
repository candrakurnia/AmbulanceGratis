package com.apps.ambulancegratis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.apps.ambulancegratis.model.RegisterResponse;
import com.apps.ambulancegratis.rest.Api;
import com.apps.ambulancegratis.rest.ApiInterface;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private TextInputEditText fullname, pass, notelp, nokk, noktp;
    ApiInterface apiInterface;
    ProgressDialog progressDialog;
    Button regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        apiInterface = Api.getClient().create(ApiInterface.class);
        fullname = findViewById(R.id.fullname);
        pass = findViewById(R.id.password);
        notelp = findViewById(R.id.no_telp);
        nokk = findViewById(R.id.no_kk);
        noktp = findViewById(R.id.no_ktp);
        regist = findViewById(R.id.register);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String full = fullname.getText().toString();
                String pw = pass.getText().toString();
                String telp = notelp.getText().toString();
                String kk = nokk.getText().toString();
                String ktp = noktp.getText().toString();

                try {
                    if (full.length() < 1) {
                        Toast.makeText(Register.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    } else if (pw.length() < 1) {
                        Toast.makeText(Register.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    } else if (telp.length() < 1) {
                        Toast.makeText(Register.this, "No Telepon tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    } else if (kk.length() < 1) {
                        Toast.makeText(Register.this, "No KK tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    } else if (ktp.length() < 1) {
                        Toast.makeText(Register.this, "No KTP tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    } else {
                        submitRegist(full,pw,telp,kk,ktp);
                    }
                } catch (Exception e) {
                    Log.d("Error", "Error" + e.getLocalizedMessage());
                }
            }
        });
    }

    private void submitRegist(String fullname,String password, String no_telpon, String no_kk, String no_ktp) {

        progressDialog = ProgressDialog.show(Register.this, null, "Harap Tunggu...", true, false);

        apiInterface.registerRequest(fullname, password, no_telpon, no_kk, no_ktp).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (!(response.body().getError())) {
                            Toast.makeText(Register.this,"Pendaftaran Berhasil",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register.this, Login.class));
                            finish();
                            progressDialog.dismiss();
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
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.e("ERROR REGISTER", "Error : " + t.getLocalizedMessage());
                handlingError("No Connection !");
            }

        });
    }


    private void handlingError(String message) {
        Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();

    }


    @Override
    public void onBackPressed() {
        Intent log = new Intent(Register.this, Login.class);
        startActivity(log);
        finish();
    }
}
