package com.example.matasolutions.pintindex;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

public class PubPageContentChild implements Parcelable {

    public final String name;
    //Optional


    public Facility facility;


    public PubPageContentChild(String name) {
        this.name = name;
    }

    public PubPageContentChild(String name,Facility facility){

        this.facility = facility;
        this.name = name;
    }



    protected PubPageContentChild(Parcel in) {
        name = in.readString();
    }

    public static final Creator<PubPageContentChild> CREATOR = new Creator<PubPageContentChild>() {
        @Override
        public PubPageContentChild createFromParcel(Parcel in) {
            return new PubPageContentChild(in);
        }

        @Override
        public PubPageContentChild[] newArray(int size) {
            return new PubPageContentChild[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeValue(facility);
    }
}