package com.example.matasolutions.pintindex;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

class Facilities implements Parcelable {

    public  ArrayList<Facility> facilities;
    public PubPageCategory category = PubPageCategory.FACILITIES;



    public Facilities(ArrayList<Facility> facilities){

        this.facilities = facilities;

    }

    public Facilities(){

    }


    public static int ReturnResourceID(Facility current){

        int answer = 0;

        switch(current.type){

            case CAR_PARKING:
                answer = R.drawable.ic_baseline_local_parking_24px;
                break;
            case LIVE_SPORTS:
                answer = R.drawable.ic_baseline_directions_run_24px;
                break;
            case FOOD_SNACKS:
                answer = R.drawable.ic_baseline_fastfood_24px;
                break;
            case FREE_WIFI:
                answer = R.drawable.ic_baseline_network_wifi_24px;
                break;
            case LIVE_MUSIC:
                answer = R.drawable.ic_baseline_music_note_24px;
                break;
            default:
                answer = 0;
        }

        return answer;

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
