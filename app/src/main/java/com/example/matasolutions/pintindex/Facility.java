package com.example.matasolutions.pintindex;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import java.io.Serializable;

class Facility implements Parcelable {

    public String name;
    public FacilityType type;
    public Drawable logo;
     PubPageCategory category = PubPageCategory.FACILITIES;


    public Facility(FacilityType type,String name){
        this.type = type;
        this.name = name;
    }

    protected Facility(Parcel in) {
        name = in.readString();
        type = (FacilityType) in.readValue(FacilityType.class.getClassLoader());
        logo = (Drawable) in.readValue(Drawable.class.getClassLoader());
        category = (PubPageCategory) in.readValue(PubPageCategory.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeValue(type);
        dest.writeValue(logo);
        dest.writeValue(category);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Facility> CREATOR = new Parcelable.Creator<Facility>() {
        @Override
        public Facility createFromParcel(Parcel in) {
            return new Facility(in);
        }

        @Override
        public Facility[] newArray(int size) {
            return new Facility[size];
        }
    };


}
