package com.example.matasolutions.pintindex;

import java.util.ArrayList;

public class WeekOpeningHours implements PubPageContentInterface {

    ArrayList<SingleOpeningHours> openingHours;

    public WeekOpeningHours(ArrayList<SingleOpeningHours> openingHours){

        this.openingHours = openingHours;


    }


    @Override
    public String ContentToString() {

        if(openingHours != null){
            StringBuilder sb = new StringBuilder();

            for(int i=0;i<openingHours.size();i++){

                SingleOpeningHours thisInstance = openingHours.get(i);

                sb.append(thisInstance.SingleOpeningHoursToString() + "\n");

            }

            return sb.toString();
        }

        else{
            return "Opening Hours not available";
        }





    }

}
