package com.example.matasolutions.pintindex;

import java.io.Serializable;
import java.nio.file.StandardWatchEventKinds;
import java.util.ArrayList;

public class WeekOpeningHours implements Serializable {

    ArrayList<SingleOpeningHours> openingHours;
    public final PubPageCategory category = PubPageCategory.OPENING_HOURS;

    public WeekOpeningHours(ArrayList<SingleOpeningHours> openingHours){

        this.openingHours = openingHours;

    }

    public WeekOpeningHours(){

    }

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
