package com.example.matasolutions.pintindex;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class Pub  {

    public LatLng coordinates;
    public String name;
    public Marker marker;
    public PubPageContent pubPageContent;

    //public Facilities facilities;

    //public Products products;

    public Pub(String name,double lat,double lng){
        this.coordinates = new LatLng(lat, lng);
        this.name = name;
    }

    public Pub(String name,double lat,double lng,PubPageContent pubPageContent){
        this.coordinates = new LatLng(lat, lng);
        this.name = name;
        this.pubPageContent = pubPageContent;
    }


}
