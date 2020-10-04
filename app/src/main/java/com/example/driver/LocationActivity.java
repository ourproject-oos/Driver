package com.example.driver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;

import androidx.annotation.Nullable;

class LocationActivity extends Service {

    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
