package com.example.matasolutions.pintindex;

import java.util.ArrayList;

public class PubSetup {

    ArrayList<Pub> pubs;

    public PubSetup(){

        pubs = new ArrayList<Pub>();
        AddPubs();

    }

    private void AddPubs(){

        Pub pub1 = new Pub("Bar Loco",54.9755819,-1.6202004);
        ArrayList<SingleOpeningHours> singleOpeningHours = new ArrayList<SingleOpeningHours>();

        singleOpeningHours.add(new SingleOpeningHours("Monday", "09:00AM", "01:00AM"));
        singleOpeningHours.add(new SingleOpeningHours("Tuesday", "09:00AM", "01:00AM"));
        singleOpeningHours.add(new SingleOpeningHours("Wednesday", "09:00AM", "01:00AM"));
        singleOpeningHours.add(new SingleOpeningHours("Thursday", "09:00AM", "01:00AM"));
        singleOpeningHours.add(new SingleOpeningHours("Friday", "09:00AM", "01:00AM"));
        singleOpeningHours.add(new SingleOpeningHours("Saturday", "09:00AM", "01:00AM"));
        singleOpeningHours.add(new SingleOpeningHours("Sunday", "09:00AM", "01:00AM"));


        WeekOpeningHours weekOpeningHours = new WeekOpeningHours(singleOpeningHours);

        pub1.setWeekOpeningHours(weekOpeningHours);
        pub1.setPrices(new Prices());
        pub1.setFacilities(new Facilities());
        pub1.setRatings(new Ratings());

        pubs.add(pub1);
        pubs.add(new Pub("Hancock",54.979915,-1.6136037));
        pubs.add(new Pub("The Strawberry",54.9748055,-1.6217146));
        pubs.add(new Pub("Trent House",54.977095,-1.6205557));
        pubs.add(new Pub("Luther's",54.9789707,-1.6172808));



    }





}
