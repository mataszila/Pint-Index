package com.example.matasolutions.pintindex;

import java.util.ArrayList;

public class WeekOpeningHours {

    ArrayList<SingleOpeningHours> openingHours;

    public WeekOpeningHours(ArrayList<SingleOpeningHours> openingHours){

        this.openingHours = openingHours;


    }

    public String WeekOpeningHoursToString(ArrayList<SingleOpeningHours> openingHours){

        StringBuilder sb = new StringBuilder();

        for(int i=0;i<openingHours.size();i++){

            SingleOpeningHours thisInstance = openingHours.get(i);

            sb.append(thisInstance.SingleOpeningHoursToString() + "\n");

        }

        return sb.toString();
    }



}
