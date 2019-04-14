package com.example.matasolutions.pintindex;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

// Represents a type of rating

class Rating implements Parcelable {

    public RatingType type;
    public double averageRating;
    public ArrayList<RatingEntry> ratingEntries;


    public Rating(RatingType type, ArrayList<RatingEntry> ratingEntries) {
        this.type = type;
        this.ratingEntries = ratingEntries;
        setAverageRating();
    }


    public Rating(){

    }


    protected Rating(Parcel in) {
        type = (RatingType) in.readValue(RatingType.class.getClassLoader());
        averageRating = in.readDouble();
        if (in.readByte() == 0x01) {
            ratingEntries = new ArrayList<RatingEntry>();
            in.readList(ratingEntries, RatingEntry.class.getClassLoader());
        } else {
            ratingEntries = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeDouble(averageRating);
        if (ratingEntries == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ratingEntries);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Rating> CREATOR = new Parcelable.Creator<Rating>() {
        @Override
        public Rating createFromParcel(Parcel in) {
            return new Rating(in);
        }

        @Override
        public Rating[] newArray(int size) {
            return new Rating[size];
        }
    };


    public void setAverageRating() {

        if (ratingEntries == null) {

            return;
        }

        double sum = 0;

        for (int i = 0; i < ratingEntries.size(); i++) {

            sum += ratingEntries.get(i).input_rating;

        }

        averageRating =  sum / ratingEntries.size();
    }



}

