package com.example.matasolutions.pintindex;

import com.google.android.gms.maps.model.LatLng;

public class Pub {

    public LatLng coordinates;

    //public OpeningHours openingHours;

    //public Facilities facilities;

    //public Products products;

    public Pub(double lat,double lng){
        this.coordinates = new LatLng(lat, lng);
    }




}
