package com.example.testmandatorymappizzaapp.handler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.example.testmandatorymappizzaapp.domain.MyMarker;
import com.example.testmandatorymappizzaapp.repository.Repository;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapHandler
{
    public static final String VALUES = "Values";
    public static final String LIST_IS_EMPTY = "list is empty";
    private GoogleMap mMap;
    private Context context;
    private Marker currentMarker;

    // For MapsActivity
    public MapHandler(GoogleMap mMap, Context context)
    {
        this.mMap = mMap;
        this.context = context;
        handleOnMapReady();
    }

    // For Repository
    public MapHandler()
    {
    }

    // Informer Repository om at starte med at lytte til databasen
    private void handleOnMapReady()
    {
        Repository.getRepository().setActivity(mMap);

        listenerOnInfoWindow();
    }


    public void addMarkersToTheMap(GoogleMap googleMap)
    {
        if (Repository.getRepository().getMyMarkerList().size() != 0)
        {
            googleMap.clear();
            // Foreach som looper objekterne i listen ud
            for (MyMarker getValues: Repository.getRepository().getMyMarkerList())
            {
                    Log.i(VALUES, "Gets the values out of the object");
                    LatLng userLocation = new LatLng(getValues.getLatitude(), getValues.getLongitude());
                    currentMarker = googleMap.addMarker(new MarkerOptions().position(userLocation).title(getValues.getUrlOfTheMarker()));
                    currentMarker.setTag(getValues.getId());
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 13));
            }
        }
        else
        {
            Log.w(LIST_IS_EMPTY, "List is empty");
        }
    }

    public void listenerOnInfoWindow()
    {
        // Listener der lytter til, hvis man klikker på infoboksen der kommer frem, når man
        // trykker på en marker.
        mMap.setOnInfoWindowClickListener(marker ->
        {
            // Get the url from the markers title
            String urlFromMarker = marker.getTitle();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(urlFromMarker));

            context.startActivity(intent);
        });
    }

}
