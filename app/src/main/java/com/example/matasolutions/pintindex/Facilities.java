package com.example.matasolutions.pintindex;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

class Facilities implements Parcelable {

    ArrayList<Facility> facilities;
    public PubPageCategory category = PubPageCategory.FACILITIES;



    public Facilities(ArrayList<Facility> facilities){

        this.facilities = facilities;

    }

    public Facilities(){

    }


    protected Facilities(Parcel in) {
        if (in.readByte() == 0x01) {
            facilities = new ArrayList<Facility>();
            in.readList(facilities, Facility.class.getClassLoader());
        } else {
            facilities = null;
        }
        category = (PubPageCategory) in.readValue(PubPageCategory.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (facilities == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(facilities);
        }
        dest.writeValue(category);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Facilities> CREATOR = new Parcelable.Creator<Facilities>() {
        @Override
        public Facilities createFromParcel(Parcel in) {
            return new Facilities(in);
        }

        @Override
        public Facilities[] newArray(int size) {
            return new Facilities[size];
        }
    };

}
