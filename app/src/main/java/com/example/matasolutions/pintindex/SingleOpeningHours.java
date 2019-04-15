package com.example.matasolutions.pintindex;

import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;

public class SingleOpeningHours implements Serializable {

    public Day dayOfTheWeek;
    public String openingTime;
    public String closingTime;


    public SingleOpeningHours(Day dayOfTheWeek,String openingTime, String closingTime){

        this.dayOfTheWeek = dayOfTheWeek;
        this.openingTime = openingTime;
        this.closingTime = closingTime;

    }

    public SingleOpeningHours(){

    }

   public String SingleOpeningHoursToString(){

        StringBuilder sb = new StringBuilder();
        sb.append(dayOfTheWeek + " ");
        sb.append(openingTime + " - ");
        sb.append(closingTime);

        return sb.toString();

   }

}