package com.example.matasolutions.pintindex;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RatingsTest {

    private Ratings ratings;


    public RatingsTest(){
    }

    @Test
    public void Test_Ratings_Null_List_Checker(){

        ratings = new Ratings(null);

        assertTrue(ratings.ratings ==null);
    }

    @Test
    public void Test_Rating_AverageRating(){

        ArrayList<Rating> ratingList = new ArrayList<>();

        ratingList.add(new Rating(RatingType.VALUE_FOR_PRICE, 5.0));
        ratingList.add(new Rating(RatingType.SERVICE, 4.0));
        ratingList.add(new Rating(RatingType.HYGIENE, 3.0));
        ratingList.add(new Rating(RatingType.ATMOSPHERE, 2.0));

        ratings = new Ratings(ratingList);

        double testRating = 3.5;
        double margin =0.0001;

        assertEquals(testRating,ratings.averageRating,margin);

    }
    
}