package com.apps.ambulancegratis;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.apps.ambulancegratis.helpers.FirebaseHelper;
import com.apps.ambulancegratis.helpers.UiHelper;
import com.apps.ambulancegratis.model.LokasiUser;
import com.apps.ambulancegratis.model.PesananResponse;
import com.apps.ambulancegratis.rest.Api;
import com.apps.ambulancegratis.rest.ApiInterface;
import com.apps.ambulancegratis.utils.SessionManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 2161;
    TextView menu, nama, alamat;
    Button pesan;
    SessionManager sessionManager;
    private UiHelper uiHelper;
    ApiInterface apiInterface;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient locationProviderClient;
    private boolean locationflag;
    
    //    private AtomicBoolean userOnlineFlag = new AtomicBoolean(false);
    private FirebaseHelper firebaseHelper;
    private String alamatText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nama = findViewById(R.id.tv_nama);
        alamat = findViewById(R.id.tv_lokasi);
        menu = findViewById(R.id.tv_menu);
        apiInterface = Api.getClient().create(ApiInterface.class);

//        kirimlokasi();

        sessionManager = new SessionManager(this);
//        firebaseHelper = new FirebaseHelper(sessionManager.getUser(this).getId_user());

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        uiHelper = new UiHelper(this);
        locationRequest = uiHelper.getLocationRequest();
        if (!uiHelper.isPlayServicesAvailable()) {
            Toast.makeText(this, "Play Services did not installed!", Toast.LENGTH_SHORT).show();
            finish();
        } else requestLocationUpdates();
        String name = sessionManager.getUser(this).getFullname();
//        String user = sessionManager.getUser(this).getId_user();
        if (name != null) {
            nama.setText(name);
        }


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prof = new Intent(MainActivity.this, Profil.class);
                startActivity(prof);
                finish();
            }
        });

    }

//    private void kirimlokasi() {
//        pesan = findViewById(R.id.btn_pesan);
//        pesan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                userOnlineFlag.set(true);
//                Toast.makeText(MainActivity.this, "Berhasil dikirim", Toast.LENGTH_SHORT).show();
////                Intent j = new Intent(MainActivity.this, WaitingScreen.class);
////                startActivity(j);
//
//            }
//        });
//    }


    private final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Location location = locationResult.getLastLocation();

            if (location == null) return;
            if (locationflag) {
                locationflag = false;
            }
            String address = "Tidak dapat menemukan lokasi";
            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

            try {
                List<Address> listAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (listAddresses != null && listAddresses.size() > 0) {
                    address = "";
                    if (listAddresses.get(0).getThoroughfare() != null) {
                        address += listAddresses.get(0).getThoroughfare() + "\n";
                    }
                    if (listAddresses.get(0).getLocality() != null) {
                        address += listAddresses.get(0).getLocality() + ", ";
                    }
                    if (listAddresses.get(0).getPostalCode() != null) {
                        address += listAddresses.get(0).getPostalCode() + ", ";
                    }
                    if (listAddresses.get(0).getAdminArea() != null) {
                        address += listAddresses.get(0).getAdminArea() + ", ";
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            alamat.setText(address);
            alamatText = address;

            pesan = findViewById(R.id.btn_pesan);
            pesan.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
//            if (userOnlineFlag.get()) {
//                firebaseHelper.updateUser(new LokasiUser(sessionManager.getUser(MainActivity.this).getId_user(), location.getLatitude(), location.getLongitude()));
                    //masukin fungsi kirim ke api
                    Call<PesananResponse> pesananResponseCall = apiInterface.pesananRequest(sessionManager.getUser(MainActivity.this).getId_user(), alamatText);
                    pesananResponseCall.enqueue(new Callback<PesananResponse>() {
                        @Override
                        public void onResponse(Call<PesananResponse> call, Response<PesananResponse> response) {
                            if (response.isSuccessful()) {
                                if (response.body() != null) {

                                    Intent j = new Intent(MainActivity.this, DriverList.class);
                                    startActivity(j);
                                    Toast.makeText(MainActivity.this, "SUKSES", Toast.LENGTH_SHORT).show();
                                    Log.e("POST", "SUKSES");
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<PesananResponse> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Gagal Terhubung", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            });
        }
    };

    @SuppressLint("MissingPermission")
    private void requestLocationUpdates() {
        if (!uiHelper.isHaveLocationPermission()) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            return;
        }
        if (uiHelper.isLocationProviderEnabled())
            uiHelper.showPositiveDialogWithListener(this, getResources().getString(R.string.enable_gps_setting), getResources().getString(R.string.location_content),
                    () -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)), "Turn On", false);
        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            int value = grantResults[0];
            if (value == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "Location Permission denied", Toast.LENGTH_SHORT).show();
                finish();
            } else if (value == PackageManager.PERMISSION_GRANTED) requestLocationUpdates();
        }
    }

   /* boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            moveTaskToBack(true);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Tekan Sekali Lagi Kalau Mau Keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }*/
}