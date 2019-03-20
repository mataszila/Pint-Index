package com.example.matasolutions.pintindex;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.core.content.res.ResourcesCompat;

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

        singleOpeningHours.add(new SingleOpeningHours(Day.MONDAY, "09:00", "00:00"));
        singleOpeningHours.add(new SingleOpeningHours(Day.TUESDAY, "09:00", "01:00"));
        singleOpeningHours.add(new SingleOpeningHours(Day.WEDNESDAY, "09:00", "01:00"));
        singleOpeningHours.add(new SingleOpeningHours(Day.THURSDAY, "09:00","01:00"));
        singleOpeningHours.add(new SingleOpeningHours(Day.FRIDAY, "09:00", "01:00"));
        singleOpeningHours.add(new SingleOpeningHours(Day.SATURDAY, "09:00", "01:00"));
        singleOpeningHours.add(new SingleOpeningHours(Day.SUNDAY, "09:00", "01:00"));

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

        // Ratings;

        ArrayList<Rating> ratings = new ArrayList<>();
        ratings.add(new Rating(RatingType.ATMOSPHERE,4.5));
        ratings.add(new Rating(RatingType.HYGIENE,4.0));
        ratings.add(new Rating(RatingType.SERVICE,3.5));
        ratings.add(new Rating(RatingType.VALUE_FOR_PRICE,3.8));


        pub1.setRatings(new Ratings(ratings));

        pubs.add(pub1);

        // End of setup for one pub.

        pubs.add(new Pub("Hancock",54.979915,-1.6136037));
        pubs.add(new Pub("The Strawberry",54.9748055,-1.6217146));
        pubs.add(new Pub("Trent House",54.977095,-1.6205557));
        pubs.add(new Pub("Luther's",54.9789707,-1.6172808));

    }


    public static int ReturnResourceID(Facility current){

        int answer = 0;

                switch(current.type){

                    case CAR_PARKING:
                        answer = R.drawable.ic_baseline_local_parking_24px;
                        break;
                    case LIVE_SPORTS:
                        answer = R.drawable.ic_baseline_directions_run_24px;
                        break;
                    case FOOD_SNACKS:
                        answer = R.drawable.ic_baseline_fastfood_24px;
                        break;
                    case FREE_WIFI:
                        answer = R.drawable.ic_baseline_network_wifi_24px;
                        break;
                    case LIVE_MUSIC:
                        answer = R.drawable.ic_baseline_music_note_24px;
                        break;
                    default:
                        answer = 0;
                }

        return answer;

    }


    public static ArrayList<PubPageContentChild> AddToChildList (ArrayList<Facility> facilities){

        if(facilities != null) {


            ArrayList<PubPageContentChild> childList = new ArrayList<>();

            for (int i = 0; i < facilities.size(); i++) {

                Facility current = facilities.get(i);

                childList.add(new PubPageContentChild(current.name, PubPageCategory.FACILITIES, current));

            }

            return childList;

        }

        return null;


    }









}
