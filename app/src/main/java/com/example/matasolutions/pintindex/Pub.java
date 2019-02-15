package com.example.matasolutions.pintindex;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.Serializable;

public class Pub  {

    public LatLng coordinates;
    public String name;
    public Marker marker;

    //public OpeningHours openingHours;

    //public Facilities facilities;

    //public Products products;

    public Pub(String name,double lat,double lng){
        this.coordinates = new LatLng(lat, lng);
        this.name = name;
    }

    public Pub(){

    }


}
