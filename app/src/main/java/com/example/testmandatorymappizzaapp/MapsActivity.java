package com.example.testmandatorymappizzaapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.testmandatorymappizzaapp.domain.MyMarker;
import com.example.testmandatorymappizzaapp.handler.MapHandler;
import com.example.testmandatorymappizzaapp.repository.Repository;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);

        checkPermissionsAndRequestUpdates();
    }


    private void checkPermissionsAndRequestUpdates()
    {
        // Checke om brugeren har givet permissions, og hvis ikke så at spørge efter det,
        // hvor man kalder på metoden requestPermissions.:
        // ContextCompat er en helper klasse til Context
        // this - hvilken context den skal have
        // Manifest.permission.ACCESS_FINE_LOCATION - hvilken permission kigger vi efter
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // ActivityCompat nedarver fra ContextCompat, så den har nogle flere metoder
            // og er en helper klasse for Activity
            // this - denne activity
            // new String[]{Manifest.permission.ACCESS_FINE_LOCATION - spørg efter permissions til FINE_LOCATION
            // 1 - requestcode, så vi ved hvem der kaldte
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        // Hvis allerede givet tilladelse
        else
        {
            initialiseMap();
        }
    }

    /**
     * Når man kommer tilbage fra metoden requestPermissions, så kan man med metoden
     * onRequestPermissionsResult fange hvad svaret var, og udføre kode.
     * Metode der fortæller om nogle requester permission og om det var ja eller nej.
     * Hvis de allerede har sagt ja, bliver denne kode ikke kørt!
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Hvis requestcode er 1
        if (requestCode == 1)
        {
            // Checke om brugeren har givet specifik denne permissions, og hvis ja, så forsæt
            // ContextCompat er en helper klasse til Context
            // this - hvilken context den skal have
            // Manifest.permission.ACCESS_FINE_LOCATION - hvilken permission kigger vi efter
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                initialiseMap();
            }
        }
    }

    // Sørger for at starte map op
    private void initialiseMap()
    {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        MapHandler mapHandler = new MapHandler(googleMap, this);
    }
}