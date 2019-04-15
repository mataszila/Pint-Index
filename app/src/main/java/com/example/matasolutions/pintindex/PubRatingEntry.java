package com.example.matasolutions.pintindex;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class PubRatingEntry implements Parcelable {

    ArrayList<RatingEntry> ratingEntries;
    String pubID;

    public PubRatingEntry(){}

    public PubRatingEntry(String pubID, ArrayList<RatingEntry> ratingEntries){
        this.ratingEntries = ratingEntries;
        this.pubID = pubID;
    }


    protected PubRatingEntry(Parcel in) {
        if (in.readByte() == 0x01) {
            ratingEntries = new ArrayList<RatingEntry>();
            in.readList(ratingEntries, RatingEntry.class.getClassLoader());
        } else {
            ratingEntries = null;
        }
        pubID = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (ratingEntries == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ratingEntries);
        }
        dest.writeString(pubID);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PubRatingEntry> CREATOR = new Parcelable.Creator<PubRatingEntry>() {
        @Override
        public PubRatingEntry createFromParcel(Parcel in) {
            return new PubRatingEntry(in);
        }

        @Override
        public PubRatingEntry[] newArray(int size) {
            return new PubRatingEntry[size];
        }
    };
}



