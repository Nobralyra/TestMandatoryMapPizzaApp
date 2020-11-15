package com.example.testmandatorymappizzaapp.repository;

import android.util.Log;

import com.example.testmandatorymappizzaapp.MapsActivity;
import com.example.testmandatorymappizzaapp.domain.MyMarker;
import com.example.testmandatorymappizzaapp.handler.MapHandler;
import com.google.android.gms.maps.GoogleMap;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Repository
{
    public static final String MARKERS = "markers";
    public static final String TAG = "Error from Firebase!";
    public static final String MARKER_DATA = "MarkerData";

    /**
     * Private så den ikke kan tilgåes af andre
     * Kan kun køres én gang
     */
    private static Repository repository = new Repository();

    private FirebaseFirestore database = FirebaseFirestore.getInstance();

    // gemmer Note objekter og kan opdateres.
    private List<MyMarker> myMarkerList = new ArrayList<>();

    //metode der udlevere klassen og opfører sig som constructor
    public static Repository getRepository()
    {
        return repository;
    }

    /**
     * Kaldes fra activity for at få hentet data ned fra FireStore
     */
    public void setActivity(GoogleMap googleMap)
    {
        startListener(googleMap);
    }

    /**
     * Getter for myMarkerList
     * @return List<MyMarker>
     */
    public List<MyMarker> getMyMarkerList()
    {
        return myMarkerList;
    }

    /**
     * SnapshotListener lytter hele tiden
     */
    public void startListener(GoogleMap googleMap)
    {
        // Fetch the collection "markers" and listen after changes
        database.collection(MARKERS).addSnapshotListener((querySnapshot, error) ->
        {
            //Tømmer listen vi har i forvejen
            myMarkerList.clear();

            if (error != null)
            {
                Log.w(TAG, "Got an exception!", error);
                return;
            }

            // A QueryDocumentSnapshot contains data read from a document in your
            // Cloud Firestore database as part of a query. The document is guaranteed to exist
            // and its data can be extracted using the getData() or the various get() methods
            // in DocumentSnapshot (such as get(String)).
            // QueryDocumentSnapshot offers the same API surface as DocumentSnapshot.
            // Since query results contain only existing documents, the exists() method will
            // always return true and getData() will never be null.
            // https://firebase.google.com/docs/reference/android/com/google/firebase/firestore/QueryDocumentSnapshot
            for (QueryDocumentSnapshot queryDocumentSnapshot : querySnapshot)
            {
                if (queryDocumentSnapshot != null)
                {
                    // Få id'en fra det aktuelle objekt
                    MyMarker myMarker = new MyMarker(
                            queryDocumentSnapshot.getId(),
                            queryDocumentSnapshot.getGeoPoint("GeographicCoordinateSystem").getLatitude(),
                            queryDocumentSnapshot.getGeoPoint("GeographicCoordinateSystem").getLongitude(),
                            queryDocumentSnapshot.getString("url"));

                    Log.i(MARKER_DATA, "Got the Marker Data");
                    myMarkerList.add(myMarker);

                    MapHandler mapHandler = new MapHandler();
                    mapHandler.addMarkersToTheMap(googleMap);
                }
            }
        });
    }

}
