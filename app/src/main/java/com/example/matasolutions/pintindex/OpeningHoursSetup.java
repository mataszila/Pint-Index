package com.example.matasolutions.pintindex;

import android.util.Pair;

import java.util.ArrayList;

public class OpeningHoursSetup {

    public ArrayList <WeekOpeningHours> fullHours;

    public OpeningHoursSetup(){

        fullHours = new ArrayList<WeekOpeningHours>();

        setupOpeningHours();

    }

    private void setupOpeningHours() {

        ArrayList<SingleOpeningHours> h1 = new ArrayList<SingleOpeningHours>();
        ArrayList<SingleOpeningHours> h2 = new ArrayList<SingleOpeningHours>();
        ArrayList<SingleOpeningHours> h3 = new ArrayList<SingleOpeningHours>();



        h1.add(new SingleOpeningHours("Monday", "09:00AM", "01:00AM"));
        h1.add(new SingleOpeningHours("Tuesday", "09:00AM", "01:00AM"));
        h1.add(new SingleOpeningHours("Wednesday", "09:00AM", "01:00AM"));
        h1.add(new SingleOpeningHours("Thursday", "09:00AM", "01:00AM"));
        h1.add(new SingleOpeningHours("Friday", "09:00AM", "01:00AM"));
        h1.add(new SingleOpeningHours("Saturday", "09:00AM", "01:00AM"));
        h1.add(new SingleOpeningHours("Sunday", "09:00AM", "01:00AM"));

        fullHours.add(new WeekOpeningHours(h1));

        h2.add(new SingleOpeningHours("Monday", "09:00AM", "01:00AM"));
        h2.add(new SingleOpeningHours("Tuesday", "09:00AM", "01:00AM"));
        h2.add(new SingleOpeningHours("Wednesday", "09:00AM", "01:00AM"));
        h2.add(new SingleOpeningHours("Thursday", "09:00AM", "01:00AM"));
        h2.add(new SingleOpeningHours("Friday", "09:00AM", "01:00AM"));
        h2.add(new SingleOpeningHours("Saturday", "09:00AM", "01:00AM"));
        h2.add(new SingleOpeningHours("Sunday", "09:00AM", "01:00AM"));

        fullHours.add(new WeekOpeningHours(h2));


        h3.add(new SingleOpeningHours("Monday", "09:00AM", "01:00AM"));
        h3.add(new SingleOpeningHours("Tuesday", "09:00AM", "01:00AM"));
        h3.add(new SingleOpeningHours("Wednesday", "09:00AM", "01:00AM"));
        h3.add(new SingleOpeningHours("Thursday", "09:00AM", "01:00AM"));
        h3.add(new SingleOpeningHours("Friday", "09:00AM", "01:00AM"));
        h3.add(new SingleOpeningHours("Saturday", "09:00AM", "01:00AM"));
        h3.add(new SingleOpeningHours("Sunday", "09:00AM", "01:00AM"));

        fullHours.add(new WeekOpeningHours(h3));





    }


}
