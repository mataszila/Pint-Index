package com.example.matasolutions.pintindex;


import android.os.Environment;

import java.lang.reflect.Array;
import java.util.ArrayList;

class Ratings implements PubPageContentInterface {

    public final PubPageCategory category = PubPageCategory.RATINGS;

    public ArrayList<Rating> ratings;
    public double averageRating;


    public Ratings(ArrayList<Rating> ratings){

        this.ratings = ratings;
        this.averageRating = calculateAverageRating();
    }

    public Ratings(){

    }



    @Override
    public String ContentToString() {

        if(ratings != null){

            StringBuilder sb = new StringBuilder();

            for(int i=0;i<ratings.size();i++){

                Rating current = ratings.get(i);


                sb.append(current.type.toString() + " " + current.rating + "\n");


            }

            return sb.toString();

        }

        else{
            return "Ratings not available";

        }

    }

    private double calculateAverageRating(){

        if(ratings == null) {

            return 0;
        }

        double sum = 0;

        for(int i=0;i<ratings.size();i++){

            sum+=ratings.get(i).rating;

        }

        return sum/ratings.size();
    }


}

