package com.example.admin.campuslocation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {
    private static final String Log_TAG = MainActivity.class.getSimpleName();
    private TextView textOutput;
    private FusedLocationProviderClient mFusedLocationClient;
    private static final int MY_PERMISION_REQUEST_FINE_LOCATION = 101;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private boolean uodateOn = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textOutput = (TextView) findViewById(R.id.detectedActivities);
        // initialize create object
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000); //  every 10 second
        mLocationRequest.setFastestInterval(5000); // if another app is using location
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationCallback = new LocationCallback();

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // completed: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // Got last known location. On some rare situations this can be null
                    if (location != null) {
                        textOutput.setText(String.valueOf(location.getLatitude()));
                    } else {
                        textOutput.setText("no lication available");

                    }
                }
            });

        }   else{
            // request permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISION_REQUEST_FINE_LOCATION);
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISION_REQUEST_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // the permission is granted, we don't have to do anything.
                }else {
                    Toast.makeText(getApplicationContext(),"This app requires locations permissions to be granted",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}


