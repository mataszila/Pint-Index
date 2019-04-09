package com.example.matasolutions.pintindex;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.net.URL;

public class Pub implements Parcelable  {

    public LatLng coordinates;
    public String name;
    public Marker marker;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String ID;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url =  url;
    }



    public Pub(String name,double lat,double lng){
        this.coordinates = new LatLng(lat, lng);
        this.name = name;

    }

    public Pub(){

    }
    protected Pub(Parcel in) {
        ID = in.readString();
        coordinates = (LatLng) in.readValue(LatLng.class.getClassLoader());
        name = in.readString();
        url = in.readString();
        weekOpeningHours = (WeekOpeningHours) in.readValue(WeekOpeningHours.class.getClassLoader());
        prices = (Prices) in.readValue(Prices.class.getClassLoader());
        facilities = (Facilities) in.readValue(Facilities.class.getClassLoader());
        ratings = (Ratings) in.readValue(Ratings.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(coordinates);
        dest.writeString(name);
        dest.writeValue(ID);
        dest.writeString(url);
        dest.writeValue(weekOpeningHours);
        dest.writeValue(prices);
        dest.writeValue(facilities);
        dest.writeValue(ratings);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Pub> CREATOR = new Parcelable.Creator<Pub>() {
        @Override
        public Pub createFromParcel(Parcel in) {
            return new Pub(in);
        }

        @Override
        public Pub[] newArray(int size) {
            return new Pub[size];
        }
    };










}
