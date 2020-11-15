package com.example.testmandatorymappizzaapp.domain;

import java.util.UUID;

public class MyMarker
{
   // Default
    private String id = UUID.randomUUID().toString();

    private double latitude;

    private double longitude;

    private String urlOfTheMarker;


    public MyMarker(String _id, double latitude, double longitude, String urlOfTheMarker)
    {

        // Når man gemmer i firebase skal alle documenter have et id, og derfor skal Note også
        // have en id. Men hvor kommer id fra? Hvis det er en nyt objekt, så laver vi den,
        // men når man henter den ned fra Firebase, så overskriver vi vores
        if (_id != null)
        {
            this.id = _id;
        }

        this.latitude = latitude;
        this.longitude = longitude;
        this.urlOfTheMarker = urlOfTheMarker;
    }

    // Bruges når der oprettes en ny MyMarker fra Android
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public String getUrlOfTheMarker()
    {
        return urlOfTheMarker;
    }

    public void setUrlOfTheMarker(String urlOfTheMarker)
    {
        this.urlOfTheMarker = urlOfTheMarker;
    }
}
