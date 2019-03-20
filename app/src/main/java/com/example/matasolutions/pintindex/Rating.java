package com.example.matasolutions.pintindex;

import java.io.Serializable;

class Rating implements Serializable {

    public RatingType type;
    public double rating;

    public Rating(RatingType type,double rating){
        this.type = type;
        this.rating = rating;
    }


}
