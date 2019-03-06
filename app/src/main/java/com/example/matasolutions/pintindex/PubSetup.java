package com.example.matasolutions.pintindex;

import java.util.ArrayList;

public class PubSetup {

    ArrayList<Pub> pubs;

    public PubSetup(){

        pubs = new ArrayList<Pub>();
        AddPubs();

    }

    private void AddPubs(){


        //Example setup for one pub:


        Pub pub1 = new Pub("Bar Loco",54.9755819,-1.6202004);

        //Opening hours


        ArrayList<SingleOpeningHours> singleOpeningHours = new ArrayList<SingleOpeningHours>();

        singleOpeningHours.add(new SingleOpeningHours("Monday", "09:00AM", "01:00AM"));
        singleOpeningHours.add(new SingleOpeningHours("Tuesday", "09:00AM", "01:00AM"));
        singleOpeningHours.add(new SingleOpeningHours("Wednesday", "09:00AM", "01:00AM"));
        singleOpeningHours.add(new SingleOpeningHours("Thursday", "09:00AM", "01:00AM"));
        singleOpeningHours.add(new SingleOpeningHours("Friday", "09:00AM", "01:00AM"));
        singleOpeningHours.add(new SingleOpeningHours("Saturday", "09:00AM", "01:00AM"));
        singleOpeningHours.add(new SingleOpeningHours("Sunday", "09:00AM", "01:00AM"));

        ProductSetup productSetup = new ProductSetup();

        //Prices

        ArrayList<Price> singlePrices = new ArrayList<Price>();

        singlePrices.add(new Price(productSetup.products.get(0),3.50));
        singlePrices.add(new Price(productSetup.products.get(1),3.75));
        singlePrices.add(new Price(productSetup.products.get(2),3.25));
        singlePrices.add(new Price(productSetup.products.get(3),4.00));

        WeekOpeningHours weekOpeningHours = new WeekOpeningHours(singleOpeningHours);

        pub1.setWeekOpeningHours(weekOpeningHours);
        pub1.setPrices(new Prices(singlePrices));

        //Facilities;

        ArrayList<Facility>  facilityInitList = new ArrayList<Facility>();

        facilityInitList.add(new Facility(FacilityType.CAR_PARKING,"Car Parking"));
        facilityInitList.add(new Facility(FacilityType.LIVE_SPORTS,"Live Sports"));



        pub1.setFacilities(new Facilities(facilityInitList));
        pub1.setRatings(new Ratings());

        pubs.add(pub1);

        // End of setup for one pub.

        pubs.add(new Pub("Hancock",54.979915,-1.6136037));
        pubs.add(new Pub("The Strawberry",54.9748055,-1.6217146));
        pubs.add(new Pub("Trent House",54.977095,-1.6205557));
        pubs.add(new Pub("Luther's",54.9789707,-1.6172808));

    }








}
