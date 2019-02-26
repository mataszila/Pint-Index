package com.example.matasolutions.pintindex;

import android.util.Pair;

import java.util.ArrayList;

public class SingleOpeningHours {

    public String dayOfTheWeek;
    public String openingTime;
    public String closingTime;


    public SingleOpeningHours(String dayOfTheWeek,String openingTime, String closingTime){

        this.dayOfTheWeek = dayOfTheWeek;
        this.openingTime = openingTime;
        this.closingTime = closingTime;

    }

   public String SingleOpeningHoursToString(){

        StringBuilder sb = new StringBuilder();
        sb.append(dayOfTheWeek + " ");
        sb.append(openingTime + " - ");
        sb.append(closingTime);

        return sb.toString();

   }

}