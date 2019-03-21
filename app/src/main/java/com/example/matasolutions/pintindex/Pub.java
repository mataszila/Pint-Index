package com.example.matasolutions.pintindex;


import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.net.MalformedURLException;
import java.net.URL;


public class Pub  {

    public LatLng coordinates;
    public String name;
    public Marker marker;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
            this.url =  url;
    }

    public String url;
    public WeekOpeningHours weekOpeningHours;
    public Prices prices;
    public Facilities facilities;
    public Ratings ratings;

    public WeekOpeningHours getWeekOpeningHours() {
        return weekOpeningHours;
    }

    public void setWeekOpeningHours(WeekOpeningHours weekOpeningHours) {
        this.weekOpeningHours = weekOpeningHours;
    }

    public Prices getPrices() {
        return prices;
    }

    public void setPrices(Prices prices) {
        this.prices = prices;
    }

    public Facilities getFacilities() {
        return facilities;
    }

    public void setFacilities(Facilities facilities) {
        this.facilities = facilities;
    }

    public Ratings getRatings() {
        return ratings;
    }

    public void setRatings(Ratings ratings) {
        this.ratings = ratings;
    }


    public Pub(String name,double lat,double lng){
        this.coordinates = new LatLng(lat, lng);
        this.name = name;

    }

    public Pub(){

    }







}
