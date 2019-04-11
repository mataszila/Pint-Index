package com.example.matasolutions.pintindex;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

public class PubSetup {

    public ArrayList<Pub> pubs;

    ArrayList<Pub> db_pubs;

    FirebaseDatabase database;
    DatabaseReference myRef;

    public PubSetup(){

        pubs = new ArrayList<Pub>();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("pubsList");

        db_pubs = new ArrayList<Pub>();

        AddPubs();

        ReadData(new MyCallback() {
            @Override
            public void onPubCallback(ArrayList<Pub> value) {
                Log.i("CALLBACK_TAG", "CALLBACK EXECUTED");
                db_pubs = value;
                Log.i("CALLBACK_TAG","DB_PUBS SIZE IS: " + db_pubs.size());
            }
        });
    }

    private void ReadData(final MyCallback myCallback){

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Pub> pubs = (ArrayList<Pub>) dataSnapshot.child("list").getValue();
                myCallback.onPubCallback(pubs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void AddPubs(){

        //Example setup for one pub:

        Pub pub1 = new Pub("Bar Loco",54.9755819,-1.6202004);

        pub1.setID(myRef.getKey());

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

        singlePrices.add(new Price(productSetup.products.get(0),3.65));
        singlePrices.add(new Price(productSetup.products.get(1),4.60));
        singlePrices.add(new Price(productSetup.products.get(2),3.65));
        singlePrices.add(new Price(productSetup.products.get(3),4.00));
        singlePrices.add(new Price(productSetup.products.get(4),3.90));

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

        ArrayList<RatingEntry> atmosphereEntries = new ArrayList<>();

        atmosphereEntries.add(new RatingEntry(RatingType.ATMOSPHERE,4.5));

        ratings.add(new Rating(RatingType.ATMOSPHERE, atmosphereEntries));


        ArrayList<RatingEntry> serviceEntries = new ArrayList<>();

        serviceEntries.add(new RatingEntry(RatingType.SERVICE,4.0));

        ratings.add(new Rating(RatingType.SERVICE, serviceEntries));


        ArrayList<RatingEntry> hygieneEntries = new ArrayList<>();

        hygieneEntries.add(new RatingEntry(RatingType.HYGIENE,4.0));

        ratings.add(new Rating(RatingType.HYGIENE, hygieneEntries));


        ArrayList<RatingEntry> valueForPriceEntries = new ArrayList<>();

        valueForPriceEntries.add(new RatingEntry(RatingType.VALUE_FOR_PRICE,4.0));

        ratings.add(new Rating(RatingType.VALUE_FOR_PRICE, valueForPriceEntries));

        pub1.setRatings(new Ratings(ratings));


        pub1.setUrl("https://media-cdn.tripadvisor.com/media/photo-s/0a/65/47/3c/bar-loco-newcastle.jpg");

        pubs.add(pub1);

        // ----------------------------------
        //
        // End of setup for one pub.
        //
        // ----------------------------------
        Pub pub2 = new Pub("Hancock",54.979915,-1.6136037);

        pub2.setID(myRef.getKey());



        ArrayList<Rating> ratings2 = new ArrayList<>();

        ArrayList<RatingEntry> atmosphereEntries2 = new ArrayList<>();

        atmosphereEntries2.add(new RatingEntry(RatingType.ATMOSPHERE,3.5));

        ratings2.add(new Rating(RatingType.ATMOSPHERE, atmosphereEntries2));


        ArrayList<RatingEntry> serviceEntries2 = new ArrayList<>();

        serviceEntries2.add(new RatingEntry(RatingType.SERVICE,3.0));

        ratings2.add(new Rating(RatingType.SERVICE, serviceEntries2));


        ArrayList<RatingEntry> hygieneEntries2 = new ArrayList<>();

        hygieneEntries2.add(new RatingEntry(RatingType.HYGIENE,3.5));

        ratings2.add(new Rating(RatingType.HYGIENE, hygieneEntries2));

        pub2.setRatings(new Ratings(ratings2));


        ArrayList<Price> singlePrices2 = new ArrayList<Price>();
        singlePrices2.add(new Price(productSetup.products.get(0),3.65));
        singlePrices2.add(new Price(productSetup.products.get(1),4.60));
        singlePrices2.add(new Price(productSetup.products.get(2),3.45));
        singlePrices2.add(new Price(productSetup.products.get(3),3.80));
        singlePrices2.add(new Price(productSetup.products.get(4),3.650));

        pub2.setPrices(new Prices(singlePrices2));

        pub2.setUrl("https://farm3.staticflickr.com/2828/8750050391_6286ccff1e_b.jpg");


        pubs.add(pub2);

        Pub pub3 = new Pub("The Strawberry",54.9748055,-1.6217146);

        pub3.setID(myRef.getKey());


        ArrayList<Price> singlePrices3 = new ArrayList<Price>();
        singlePrices3.add(new Price(productSetup.products.get(0),4.30));
        singlePrices3.add(new Price(productSetup.products.get(1),4.60));
        singlePrices3.add(new Price(productSetup.products.get(2),3.95));
        singlePrices3.add(new Price(productSetup.products.get(3),3.20));
        singlePrices3.add(new Price(productSetup.products.get(4),3.15));

        pub3.setPrices(new Prices(singlePrices3));


        Pub pub4 = new Pub("Trent House",54.977095,-1.6205557);

        pub4.setID(myRef.getKey());


        ArrayList<Price> singlePrices4 = new ArrayList<Price>();
        singlePrices4.add(new Price(productSetup.products.get(0),3.85));
        singlePrices4.add(new Price(productSetup.products.get(1),4.60));
        singlePrices4.add(new Price(productSetup.products.get(2),3.85));
        singlePrices4.add(new Price(productSetup.products.get(3),4.05));
        singlePrices4.add(new Price(productSetup.products.get(4),3.650));

        pub4.setPrices(new Prices(singlePrices4));


        Pub pub5 = new Pub("Luther's",54.9789707,-1.6172808);

        ArrayList<Price> singlePrices5 = new ArrayList<Price>();
        singlePrices5.add(new Price(productSetup.products.get(0),3.35));
        singlePrices5.add(new Price(productSetup.products.get(1),4.60));
        singlePrices5.add(new Price(productSetup.products.get(2),3.25));
        singlePrices5.add(new Price(productSetup.products.get(3),3.70));
        singlePrices5.add(new Price(productSetup.products.get(4),3.650));

        pub5.setPrices(new Prices(singlePrices5));

        pub5.setID(myRef.getKey());


        pubs.add(pub3);

        pubs.add(pub4);
        pubs.add(pub5);

        //myRef.child("list").setValue(pubs);




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

    public Pub returnPubByName(String name){

        for(Pub p : pubs){
            if(name.equals(p.name)){
                return p;
            }
        }

        return new Pub();




    }









}
