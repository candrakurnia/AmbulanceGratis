package com.apps.ambulancegratis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.apps.ambulancegratis.adapter.RVAdapterDriver;
import com.apps.ambulancegratis.model.Driver;
import com.apps.ambulancegratis.model.DriverResponse;
import com.apps.ambulancegratis.model.User;
import com.apps.ambulancegratis.rest.Api;
import com.apps.ambulancegratis.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverList extends AppCompatActivity {

    TextView bck;
    ApiInterface apiInterface;
    private  List<Driver> drivers = new ArrayList<>();
    private RVAdapterDriver rvAdapterDriver;
    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_list);

        apiInterface = Api.getClient().create(ApiInterface.class);
        recyclerView = findViewById(R.id.rv_driver);
        rvAdapterDriver = new RVAdapterDriver(drivers,this);
        RecyclerView.LayoutManager mlayoutmanager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(mlayoutmanager);
        recyclerView.setAdapter(rvAdapterDriver);
        bck = findViewById(R.id.tv_kembali);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bk = new Intent(DriverList.this, MainActivity.class);
                startActivity(bk);
                finish();
            }
        });

        loadDriver();
    }

    private void loadDriver() {
        Call<DriverResponse> call = apiInterface.driverRequest();
        call.enqueue(new Callback<DriverResponse>() {
            @Override
            public void onResponse(Call<DriverResponse> call, Response<DriverResponse> response) {
                String value = response.body().getValue();
                if (value.equals("4")) {
                    drivers = response.body().getDriver();
                    rvAdapterDriver = new RVAdapterDriver(drivers, DriverList.this);
                    recyclerView.setAdapter(rvAdapterDriver);

                }
            }

            @Override
            public void onFailure(Call<DriverResponse> call, Throwable t) {
                Log.e("ERROR", t.getLocalizedMessage());
            }
        });
    }

}