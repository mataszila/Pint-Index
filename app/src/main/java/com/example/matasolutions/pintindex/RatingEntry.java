package com.example.matasolutions.pintindex;

import com.example.matasolutions.pintindex.RatingType;

import java.io.Serializable;

//Represents one entry of one rating type.


public class RatingEntry implements Serializable {

    public RatingType ratingType;
    public double input_rating;

    public RatingEntry(RatingType ratingType,double rating){

        this.ratingType = ratingType;
        this.input_rating = rating;
    }

    public RatingEntry(RatingType ratingType){
        this.ratingType = ratingType;

    }
    public RatingEntry(){

    }




}
