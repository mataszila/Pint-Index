package com.example.matasolutions.pintindex;

import java.util.ArrayList;

public class PubRatingEntry {

    ArrayList<RatingEntry> ratingEntries;
    String pubID;

    public PubRatingEntry(){}

    public PubRatingEntry(String pubID, ArrayList<RatingEntry> ratingEntries){
        this.ratingEntries = ratingEntries;
        this.pubID = pubID;
    }

}
