package com.example.matasolutions.pintindex;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.matasolutions.pintindex.RatingType;

import java.io.Serializable;

//Represents one entry of one rating type.


public class RatingEntry implements Parcelable {

    public String ratingType;
    public double input_rating;


    RatingEntry(RatingType ratingType,double rating){

        this.ratingType = ratingType.name();
        this.input_rating = rating;
    }

    public RatingEntry(RatingType ratingType){
        this.ratingType = ratingType.name();

    }
    public RatingEntry(){

    }
    protected RatingEntry(Parcel in) {
        ratingType = in.readString();
        input_rating = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ratingType);
        dest.writeDouble(input_rating);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<RatingEntry> CREATOR = new Parcelable.Creator<RatingEntry>() {
        @Override
        public RatingEntry createFromParcel(Parcel in) {
            return new RatingEntry(in);
        }

        @Override
        public RatingEntry[] newArray(int size) {
            return new RatingEntry[size];
        }
    };
}









