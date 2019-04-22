package com.example.matasolutions.pintindex;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
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

public class PubSetup implements Parcelable {

    public ArrayList<Pub> pubs;

    ArrayList<Pub> db_pubs;

    FirebaseDatabase database;
    DatabaseReference myRef;

    PubSetup(){

        pubs = new ArrayList<Pub>();

        SetupDatabase();

        db_pubs = new ArrayList<Pub>();

        AddPubs();

    }

    PubSetup(boolean testing){

        pubs = new ArrayList<Pub>();
        AddPubs();

    }


    private void SetupDatabase(){

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("pubsList");
    }

    protected PubSetup(Parcel in) {
        if (in.readByte() == 0x01) {
            pubs = new ArrayList<Pub>();
            in.readList(pubs, Pub.class.getClassLoader());
        } else {
            pubs = null;
        }
        if (in.readByte() == 0x01) {
            db_pubs = new ArrayList<Pub>();
            in.readList(db_pubs, Pub.class.getClassLoader());
        } else {
            db_pubs = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (pubs == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(pubs);
        }
        if (db_pubs == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(db_pubs);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<PubSetup> CREATOR = new Parcelable.Creator<PubSetup>() {
        @Override
        public PubSetup createFromParcel(Parcel in) {
            return new PubSetup(in);
        }

        @Override
        public PubSetup[] newArray(int size) {
            return new PubSetup[size];
        }
    };




     void ReadData(final MyCallback myCallback){

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Pub> db_pubs = ConvertSnapshot(dataSnapshot.child("list"));
                myCallback.onPubCallback(db_pubs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private ArrayList<Pub> ConvertSnapshot(DataSnapshot dataSnapshot){

        ArrayList<Pub> list = new ArrayList<>();

        for(DataSnapshot snap : dataSnapshot.getChildren()){

            Pub pub =   snap.getValue(Pub.class);
            list.add(pub);

        }
        return list;

    }


    private void AddPubs(){

        //Example setup for one pub:

        Pub pub1 = new Pub("Bar Loco",54.9755819,-1.6202004);

        pub1.setID("0");

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

        // Pub 2 - Hancock


        Pub pub2 = new Pub("Hancock",54.979915,-1.6136037);

        pub2.setID("1");

        //Opening hours

        ArrayList<SingleOpeningHours> singleOpeningHours2 = new ArrayList<SingleOpeningHours>();

        singleOpeningHours2.add(new SingleOpeningHours(Day.MONDAY, "09:00", "00:00"));
        singleOpeningHours2.add(new SingleOpeningHours(Day.TUESDAY, "09:00", "01:00"));
        singleOpeningHours2.add(new SingleOpeningHours(Day.WEDNESDAY, "09:00", "01:30"));
        singleOpeningHours2.add(new SingleOpeningHours(Day.THURSDAY, "09:00","01:00"));
        singleOpeningHours2.add(new SingleOpeningHours(Day.FRIDAY, "09:00", "01:00"));
        singleOpeningHours2.add(new SingleOpeningHours(Day.SATURDAY, "09:00", "01:00"));
        singleOpeningHours2.add(new SingleOpeningHours(Day.SUNDAY, "09:00", "01:00"));

        //Prices

        //Prices

        // (Brand.FOSTERS, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.BIRRA_MORRETI, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.HEINEKEN, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.PERONI, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.STELLA_ARTOIS, DrinkType.BEER,Amount.PINT));

        ArrayList<Price> singlePrices2 = new ArrayList<Price>();

        singlePrices2.add(new Price(productSetup.products.get(0),3.00));
        singlePrices2.add(new Price(productSetup.products.get(1),3.20));
        singlePrices2.add(new Price(productSetup.products.get(2),3.90));
        singlePrices2.add(new Price(productSetup.products.get(3),3.80));
        singlePrices2.add(new Price(productSetup.products.get(4),3.50));

        WeekOpeningHours weekOpeningHours2 = new WeekOpeningHours(singleOpeningHours2);

        pub2.setWeekOpeningHours(weekOpeningHours2);
        pub2.setPrices(new Prices(singlePrices2));

        //Facilities;

        ArrayList<Facility>  facilityInitList2 = new ArrayList<Facility>();
        facilityInitList2.add(new Facility(FacilityType.CAR_PARKING,"Car Parking"));
        facilityInitList2.add(new Facility(FacilityType.LIVE_SPORTS,"Live Sports"));

        pub2.setFacilities(new Facilities(facilityInitList2));

        // Ratings;

        ArrayList<Rating> ratings2 = new ArrayList<>();

        ArrayList<RatingEntry> atmosphereEntries2 = new ArrayList<>();

        atmosphereEntries2.add(new RatingEntry(RatingType.ATMOSPHERE,4.5));

        ratings2.add(new Rating(RatingType.ATMOSPHERE, atmosphereEntries2));


        ArrayList<RatingEntry> serviceEntries2 = new ArrayList<>();

        serviceEntries2.add(new RatingEntry(RatingType.SERVICE,3.5));

        ratings2.add(new Rating(RatingType.SERVICE, serviceEntries2));


        ArrayList<RatingEntry> hygieneEntries2 = new ArrayList<>();

        hygieneEntries2.add(new RatingEntry(RatingType.HYGIENE,3.5));

        ratings2.add(new Rating(RatingType.HYGIENE, hygieneEntries2));


        ArrayList<RatingEntry> valueForPriceEntries2 = new ArrayList<>();

        valueForPriceEntries2.add(new RatingEntry(RatingType.VALUE_FOR_PRICE,4.5));

        ratings2.add(new Rating(RatingType.VALUE_FOR_PRICE, valueForPriceEntries2));

        pub2.setRatings(new Ratings(ratings2));


        pub2.setUrl("https://i2-prod.chroniclelive.co.uk/incoming/article15200272.ece/ALTERNATES/s1227b/0_image002-1.jpg");

        pubs.add(pub2);


        // ----------------------------------
        //
        // End of setup for one pub.
        //
        // ----------------------------------


        // Pub 3 - The Strawberry

        Pub pub3 = new Pub("The Strawberry",54.9748055,-1.6217146);

        pub3.setID("2");

        //Opening hours

        ArrayList<SingleOpeningHours> singleOpeningHours3 = new ArrayList<SingleOpeningHours>();

        singleOpeningHours3.add(new SingleOpeningHours(Day.MONDAY, "11:00", "00:00"));
        singleOpeningHours3.add(new SingleOpeningHours(Day.TUESDAY, "11:00", "01:00"));
        singleOpeningHours3.add(new SingleOpeningHours(Day.WEDNESDAY, "11:00", "00:00"));
        singleOpeningHours3.add(new SingleOpeningHours(Day.THURSDAY, "11:00","01:00"));
        singleOpeningHours3.add(new SingleOpeningHours(Day.FRIDAY, "11:00", "02:00"));
        singleOpeningHours3.add(new SingleOpeningHours(Day.SATURDAY, "11:00", "02:00"));
        singleOpeningHours3.add(new SingleOpeningHours(Day.SUNDAY, "11:00", "01:00"));


        //Prices

        // (Brand.FOSTERS, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.BIRRA_MORRETI, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.HEINEKEN, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.PERONI, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.STELLA_ARTOIS, DrinkType.BEER,Amount.PINT));

        ArrayList<Price> singlePrices3 = new ArrayList<Price>();

        singlePrices3.add(new Price(productSetup.products.get(0),3.45));
        singlePrices3.add(new Price(productSetup.products.get(1),4.30));
        singlePrices3.add(new Price(productSetup.products.get(2),3.60));
        singlePrices3.add(new Price(productSetup.products.get(3),4.15));
        singlePrices3.add(new Price(productSetup.products.get(4),3.80));

        WeekOpeningHours weekOpeningHours3 = new WeekOpeningHours(singleOpeningHours3);

        pub3.setWeekOpeningHours(weekOpeningHours3);
        pub3.setPrices(new Prices(singlePrices3));

        //Facilities;

        ArrayList<Facility>  facilityInitList3 = new ArrayList<Facility>();
        facilityInitList3.add(new Facility(FacilityType.CAR_PARKING,"Car Parking"));
        facilityInitList3.add(new Facility(FacilityType.LIVE_SPORTS,"Live Sports"));

        pub3.setFacilities(new Facilities(facilityInitList3));

        // Ratings;

        ArrayList<Rating> ratings3 = new ArrayList<>();

        ArrayList<RatingEntry> atmosphereEntries3 = new ArrayList<>();

        atmosphereEntries3.add(new RatingEntry(RatingType.ATMOSPHERE,3));

        ratings3.add(new Rating(RatingType.ATMOSPHERE, atmosphereEntries3));


        ArrayList<RatingEntry> serviceEntries3 = new ArrayList<>();

        serviceEntries3.add(new RatingEntry(RatingType.SERVICE,3.5));

        ratings3.add(new Rating(RatingType.SERVICE, serviceEntries3));


        ArrayList<RatingEntry> hygieneEntries3 = new ArrayList<>();

        hygieneEntries3.add(new RatingEntry(RatingType.HYGIENE,3.5));

        ratings3.add(new Rating(RatingType.HYGIENE, hygieneEntries3));


        ArrayList<RatingEntry> valueForPriceEntries3 = new ArrayList<>();

        valueForPriceEntries3.add(new RatingEntry(RatingType.VALUE_FOR_PRICE,4.0));

        ratings3.add(new Rating(RatingType.VALUE_FOR_PRICE, valueForPriceEntries3));

        pub3.setRatings(new Ratings(ratings3));


        pub3.setUrl("https://9968c6ef49dc043599a5-e151928c3d69a5a4a2d07a8bf3efa90a.ssl.cf2.rackcdn.com/70700-3.jpg");

        pubs.add(pub3);

        // ----------------------------------
        //
        // End of setup for one pub.
        //
        // ----------------------------------


        // Pub 4 - Trent House

        Pub pub4 = new Pub("Trent House",54.977095,-1.6205557);

        pub4.setID("3");

        //Opening hours

        ArrayList<SingleOpeningHours> singleOpeningHours4 = new ArrayList<SingleOpeningHours>();

        singleOpeningHours4.add(new SingleOpeningHours(Day.MONDAY, "11:00", "00:00"));
        singleOpeningHours4.add(new SingleOpeningHours(Day.TUESDAY, "11:00", "01:00"));
        singleOpeningHours4.add(new SingleOpeningHours(Day.WEDNESDAY, "11:00", "00:30"));
        singleOpeningHours4.add(new SingleOpeningHours(Day.THURSDAY, "11:00","01:00"));
        singleOpeningHours4.add(new SingleOpeningHours(Day.FRIDAY, "11:00", "02:00"));
        singleOpeningHours4.add(new SingleOpeningHours(Day.SATURDAY, "11:00", "02:00"));
        singleOpeningHours4.add(new SingleOpeningHours(Day.SUNDAY, "11:00", "01:00"));


        //Prices

        // (Brand.FOSTERS, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.BIRRA_MORRETI, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.HEINEKEN, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.PERONI, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.STELLA_ARTOIS, DrinkType.BEER,Amount.PINT));

        ArrayList<Price> singlePrices4 = new ArrayList<Price>();

        singlePrices4.add(new Price(productSetup.products.get(0),3.50));
        singlePrices4.add(new Price(productSetup.products.get(1),4.50));
        singlePrices4.add(new Price(productSetup.products.get(2),3.80));
        singlePrices4.add(new Price(productSetup.products.get(3),4.20));
        singlePrices4.add(new Price(productSetup.products.get(4),3.90));

        WeekOpeningHours weekOpeningHours4 = new WeekOpeningHours(singleOpeningHours4);

        pub4.setWeekOpeningHours(weekOpeningHours4);
        pub4.setPrices(new Prices(singlePrices4));

        //Facilities;

        ArrayList<Facility>  facilityInitList4 = new ArrayList<Facility>();
        facilityInitList4.add(new Facility(FacilityType.CAR_PARKING,"Car Parking"));
        facilityInitList4.add(new Facility(FacilityType.LIVE_SPORTS,"Live Sports"));

        pub4.setFacilities(new Facilities(facilityInitList4));

        // Ratings;

        ArrayList<Rating> ratings4 = new ArrayList<>();

        ArrayList<RatingEntry> atmosphereEntries4 = new ArrayList<>();

        atmosphereEntries4.add(new RatingEntry(RatingType.ATMOSPHERE,3.5));

        ratings4.add(new Rating(RatingType.ATMOSPHERE, atmosphereEntries4));


        ArrayList<RatingEntry> serviceEntries4 = new ArrayList<>();

        serviceEntries4.add(new RatingEntry(RatingType.SERVICE,3));

        ratings4.add(new Rating(RatingType.SERVICE, serviceEntries4));


        ArrayList<RatingEntry> hygieneEntries4 = new ArrayList<>();

        hygieneEntries4.add(new RatingEntry(RatingType.HYGIENE,4.0));

        ratings4.add(new Rating(RatingType.HYGIENE, hygieneEntries4));


        ArrayList<RatingEntry> valueForPriceEntries4 = new ArrayList<>();

        valueForPriceEntries4.add(new RatingEntry(RatingType.VALUE_FOR_PRICE,5));

        ratings4.add(new Rating(RatingType.VALUE_FOR_PRICE, valueForPriceEntries4));

        pub4.setRatings(new Ratings(ratings4));


        pub4.setUrl("https://www.digitalhealth.net/wp-content/uploads/2017/01/Trent_house-newcastle.jpg");

        pubs.add(pub4);

        // ----------------------------------
        //
        // End of setup for one pub.
        //
        // ----------------------------------


        // Pub 5 - Luther's

        Pub pub5 = new Pub("Luther's",54.9789707,-1.6172808);

        pub5.setID("4");

        //Opening hours

        ArrayList<SingleOpeningHours> singleOpeningHours5 = new ArrayList<SingleOpeningHours>();

        singleOpeningHours5.add(new SingleOpeningHours(Day.MONDAY, "11:00", "00:00"));
        singleOpeningHours5.add(new SingleOpeningHours(Day.TUESDAY, "11:00", "01:00"));
        singleOpeningHours5.add(new SingleOpeningHours(Day.WEDNESDAY, "11:00", "23:00"));
        singleOpeningHours5.add(new SingleOpeningHours(Day.THURSDAY, "11:00","01:00"));
        singleOpeningHours5.add(new SingleOpeningHours(Day.FRIDAY, "11:00", "02:00"));
        singleOpeningHours5.add(new SingleOpeningHours(Day.SATURDAY, "11:00", "02:00"));
        singleOpeningHours5.add(new SingleOpeningHours(Day.SUNDAY, "11:00", "01:00"));

        //Prices

        // (Brand.FOSTERS, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.BIRRA_MORRETI, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.HEINEKEN, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.PERONI, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.STELLA_ARTOIS, DrinkType.BEER,Amount.PINT));

        ArrayList<Price> singlePrices5 = new ArrayList<Price>();

        singlePrices5.add(new Price(productSetup.products.get(0),3.30));
        singlePrices5.add(new Price(productSetup.products.get(1),4.60));
        singlePrices5.add(new Price(productSetup.products.get(2),3.70));
        singlePrices5.add(new Price(productSetup.products.get(3),4.50));
        singlePrices5.add(new Price(productSetup.products.get(4),4.00));

        WeekOpeningHours weekOpeningHours5 = new WeekOpeningHours(singleOpeningHours5);

        pub5.setWeekOpeningHours(weekOpeningHours5);
        pub5.setPrices(new Prices(singlePrices5));

        //Facilities;

        ArrayList<Facility>  facilityInitList5 = new ArrayList<Facility>();
        facilityInitList5.add(new Facility(FacilityType.CAR_PARKING,"Car Parking"));
        facilityInitList5.add(new Facility(FacilityType.LIVE_SPORTS,"Live Sports"));

        pub5.setFacilities(new Facilities(facilityInitList5));

        // Ratings;

        ArrayList<Rating> ratings5 = new ArrayList<>();

        ArrayList<RatingEntry> atmosphereEntries5 = new ArrayList<>();

        atmosphereEntries5.add(new RatingEntry(RatingType.ATMOSPHERE,2.5));

        ratings5.add(new Rating(RatingType.ATMOSPHERE, atmosphereEntries5));


        ArrayList<RatingEntry> serviceEntries5 = new ArrayList<>();

        serviceEntries5.add(new RatingEntry(RatingType.SERVICE,3.5));

        ratings5.add(new Rating(RatingType.SERVICE, serviceEntries5));


        ArrayList<RatingEntry> hygieneEntries5 = new ArrayList<>();

        hygieneEntries5.add(new RatingEntry(RatingType.HYGIENE,4.5));

        ratings5.add(new Rating(RatingType.HYGIENE, hygieneEntries5));


        ArrayList<RatingEntry> valueForPriceEntries5 = new ArrayList<>();

        valueForPriceEntries5.add(new RatingEntry(RatingType.VALUE_FOR_PRICE,3.5));

        ratings5.add(new Rating(RatingType.VALUE_FOR_PRICE, valueForPriceEntries5));

        pub5.setRatings(new Ratings(ratings5));


        pub5.setUrl("https://www.nusu.co.uk/asset/News/6013/MensBarnamechange.jpg?thumbnail_width=1336&thumbnail_height=679&resize_type=CropToFit");

        pubs.add(pub5);



        // ----------------------------------
        //
        // End of setup for one pub.
        //
        // ----------------------------------


        // Pub 6 - Hotspur

        Pub pub6 = new Pub("Hotspur",54.97709,-1.6172267);

        pub6.setID("5");

        //Opening hours

        ArrayList<SingleOpeningHours> singleOpeningHours6 = new ArrayList<SingleOpeningHours>();

        singleOpeningHours6.add(new SingleOpeningHours(Day.MONDAY, "11:00", "00:00"));
        singleOpeningHours6.add(new SingleOpeningHours(Day.TUESDAY, "11:00", "01:00"));
        singleOpeningHours6.add(new SingleOpeningHours(Day.WEDNESDAY, "11:00", "01:00"));
        singleOpeningHours6.add(new SingleOpeningHours(Day.THURSDAY, "11:00","01:00"));
        singleOpeningHours6.add(new SingleOpeningHours(Day.FRIDAY, "11:00", "02:00"));
        singleOpeningHours6.add(new SingleOpeningHours(Day.SATURDAY, "11:00", "02:00"));
        singleOpeningHours6.add(new SingleOpeningHours(Day.SUNDAY, "11:00", "01:00"));

        //Prices

        // (Brand.FOSTERS, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.BIRRA_MORRETI, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.HEINEKEN, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.PERONI, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.STELLA_ARTOIS, DrinkType.BEER,Amount.PINT));

        ArrayList<Price> singlePrices6 = new ArrayList<Price>();

        singlePrices6.add(new Price(productSetup.products.get(0),2.75));
        singlePrices6.add(new Price(productSetup.products.get(1),4.00));
        singlePrices6.add(new Price(productSetup.products.get(2),3.50));
        singlePrices6.add(new Price(productSetup.products.get(3),4.50));
        singlePrices6.add(new Price(productSetup.products.get(4),3.50));

        WeekOpeningHours weekOpeningHours6 = new WeekOpeningHours(singleOpeningHours6);

        pub6.setWeekOpeningHours(weekOpeningHours6);
        pub6.setPrices(new Prices(singlePrices6));

        //Facilities;

        ArrayList<Facility>  facilityInitList6 = new ArrayList<Facility>();
        facilityInitList6.add(new Facility(FacilityType.CAR_PARKING,"Car Parking"));
        facilityInitList6.add(new Facility(FacilityType.LIVE_SPORTS,"Live Sports"));

        pub6.setFacilities(new Facilities(facilityInitList6));

        // Ratings;

        ArrayList<Rating> ratings6 = new ArrayList<>();

        ArrayList<RatingEntry> atmosphereEntries6 = new ArrayList<>();

        atmosphereEntries6.add(new RatingEntry(RatingType.ATMOSPHERE,4.5));

        ratings6.add(new Rating(RatingType.ATMOSPHERE, atmosphereEntries6));


        ArrayList<RatingEntry> serviceEntries6 = new ArrayList<>();

        serviceEntries6.add(new RatingEntry(RatingType.SERVICE,3.5));

        ratings6.add(new Rating(RatingType.SERVICE, serviceEntries6));


        ArrayList<RatingEntry> hygieneEntries6 = new ArrayList<>();

        hygieneEntries6.add(new RatingEntry(RatingType.HYGIENE,4.5));

        ratings6.add(new Rating(RatingType.HYGIENE, hygieneEntries6));


        ArrayList<RatingEntry> valueForPriceEntries6 = new ArrayList<>();

        valueForPriceEntries6.add(new RatingEntry(RatingType.VALUE_FOR_PRICE,4.0));

        ratings6.add(new Rating(RatingType.VALUE_FOR_PRICE, valueForPriceEntries6));

        pub6.setRatings(new Ratings(ratings6));


        pub6.setUrl("https://whatpub.com/img/TYN/3928/hotspur-newcastle-upon-tyne/298/224");

        pubs.add(pub6);



        // ----------------------------------
        //
        // End of setup for one pub.
        //
        // ----------------------------------


        // Pub 7 - Alvino's

        Pub pub7 = new Pub("Alvino's",54.9720298,-1.6127201);

        pub7.setID("6");

        //Opening hours

        ArrayList<SingleOpeningHours> singleOpeningHours7 = new ArrayList<SingleOpeningHours>();

        singleOpeningHours7.add(new SingleOpeningHours(Day.MONDAY, "11:00", "00:00"));
        singleOpeningHours7.add(new SingleOpeningHours(Day.TUESDAY, "11:00", "01:00"));
        singleOpeningHours7.add(new SingleOpeningHours(Day.WEDNESDAY, "11:00", "02:00"));
        singleOpeningHours7.add(new SingleOpeningHours(Day.THURSDAY, "11:00","01:00"));
        singleOpeningHours7.add(new SingleOpeningHours(Day.FRIDAY, "11:00", "02:00"));
        singleOpeningHours7.add(new SingleOpeningHours(Day.SATURDAY, "11:00", "02:00"));
        singleOpeningHours7.add(new SingleOpeningHours(Day.SUNDAY, "11:00", "01:00"));

        //Prices

        // (Brand.FOSTERS, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.BIRRA_MORRETI, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.HEINEKEN, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.PERONI, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.STELLA_ARTOIS, DrinkType.BEER,Amount.PINT));

        ArrayList<Price> singlePrices7 = new ArrayList<Price>();

        singlePrices7.add(new Price(productSetup.products.get(0),3.8));
        singlePrices7.add(new Price(productSetup.products.get(1),4.9));
        singlePrices7.add(new Price(productSetup.products.get(2),4.1));
        singlePrices7.add(new Price(productSetup.products.get(3),4.7));
        singlePrices7.add(new Price(productSetup.products.get(4),4.2));

        WeekOpeningHours weekOpeningHours7 = new WeekOpeningHours(singleOpeningHours7);

        pub7.setWeekOpeningHours(weekOpeningHours7);
        pub7.setPrices(new Prices(singlePrices7));

        //Facilities;

        ArrayList<Facility>  facilityInitList7 = new ArrayList<Facility>();
        facilityInitList7.add(new Facility(FacilityType.CAR_PARKING,"Car Parking"));
        facilityInitList7.add(new Facility(FacilityType.LIVE_SPORTS,"Live Sports"));

        pub7.setFacilities(new Facilities(facilityInitList7));

        // Ratings;

        ArrayList<Rating> ratings7 = new ArrayList<>();

        ArrayList<RatingEntry> atmosphereEntries7= new ArrayList<>();

        atmosphereEntries7.add(new RatingEntry(RatingType.ATMOSPHERE,3.5));

        ratings7.add(new Rating(RatingType.ATMOSPHERE, atmosphereEntries7));


        ArrayList<RatingEntry> serviceEntries7 = new ArrayList<>();

        serviceEntries7.add(new RatingEntry(RatingType.SERVICE,3));

        ratings7.add(new Rating(RatingType.SERVICE, serviceEntries7));


        ArrayList<RatingEntry> hygieneEntries7 = new ArrayList<>();

        hygieneEntries7.add(new RatingEntry(RatingType.HYGIENE,4.5));

        ratings7.add(new Rating(RatingType.HYGIENE, hygieneEntries7));


        ArrayList<RatingEntry> valueForPriceEntries7 = new ArrayList<>();

        valueForPriceEntries7.add(new RatingEntry(RatingType.VALUE_FOR_PRICE,3));

        ratings7.add(new Rating(RatingType.VALUE_FOR_PRICE, valueForPriceEntries7));

        pub7.setRatings(new Ratings(ratings7));


        pub7.setUrl("https://www.top50cocktailbars.com/wp-content/uploads/2018/05/DSC_1332_copy.jpg");

        pubs.add(pub7);


        // ----------------------------------
        //
        // End of setup for one pub.
        //
        // ----------------------------------


        // Pub 8 - Zerox

        Pub pub8 = new Pub("Zerox",54.9688825,-1.6144045);

        pub8.setID("7");

        //Opening hours

        ArrayList<SingleOpeningHours> singleOpeningHours8 = new ArrayList<SingleOpeningHours>();

        singleOpeningHours8.add(new SingleOpeningHours(Day.MONDAY, "11:00", "00:00"));
        singleOpeningHours8.add(new SingleOpeningHours(Day.TUESDAY, "11:00", "01:00"));
        singleOpeningHours8.add(new SingleOpeningHours(Day.WEDNESDAY, "11:00", "01:30"));
        singleOpeningHours8.add(new SingleOpeningHours(Day.THURSDAY, "11:00","01:00"));
        singleOpeningHours8.add(new SingleOpeningHours(Day.FRIDAY, "11:00", "02:00"));
        singleOpeningHours8.add(new SingleOpeningHours(Day.SATURDAY, "11:00", "02:00"));
        singleOpeningHours8.add(new SingleOpeningHours(Day.SUNDAY, "11:00", "01:00"));

        //Prices

        // (Brand.FOSTERS, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.BIRRA_MORRETI, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.HEINEKEN, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.PERONI, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.STELLA_ARTOIS, DrinkType.BEER,Amount.PINT));

        ArrayList<Price> singlePrices8 = new ArrayList<Price>();

        singlePrices8.add(new Price(productSetup.products.get(0),4.2));
        singlePrices8.add(new Price(productSetup.products.get(1),5.5));
        singlePrices8.add(new Price(productSetup.products.get(2),4.2));
        singlePrices8.add(new Price(productSetup.products.get(3),5.2));
        singlePrices8.add(new Price(productSetup.products.get(4),4.7));

        WeekOpeningHours weekOpeningHours8 = new WeekOpeningHours(singleOpeningHours8);

        pub8.setWeekOpeningHours(weekOpeningHours8);
        pub8.setPrices(new Prices(singlePrices8));

        //Facilities;

        ArrayList<Facility>  facilityInitList8 = new ArrayList<Facility>();
        facilityInitList8.add(new Facility(FacilityType.CAR_PARKING,"Car Parking"));
        facilityInitList8.add(new Facility(FacilityType.LIVE_SPORTS,"Live Sports"));

        pub8.setFacilities(new Facilities(facilityInitList8));

        // Ratings;

        ArrayList<Rating> ratings8 = new ArrayList<>();

        ArrayList<RatingEntry> atmosphereEntries8= new ArrayList<>();

        atmosphereEntries8.add(new RatingEntry(RatingType.ATMOSPHERE,4));

        ratings8.add(new Rating(RatingType.ATMOSPHERE, atmosphereEntries8));


        ArrayList<RatingEntry> serviceEntries8 = new ArrayList<>();

        serviceEntries8.add(new RatingEntry(RatingType.SERVICE,4.5));

        ratings8.add(new Rating(RatingType.SERVICE, serviceEntries8));


        ArrayList<RatingEntry> hygieneEntries8 = new ArrayList<>();

        hygieneEntries8.add(new RatingEntry(RatingType.HYGIENE,4.5));

        ratings8.add(new Rating(RatingType.HYGIENE, hygieneEntries8));


        ArrayList<RatingEntry> valueForPriceEntries8 = new ArrayList<>();

        valueForPriceEntries8.add(new RatingEntry(RatingType.VALUE_FOR_PRICE,3));

        ratings8.add(new Rating(RatingType.VALUE_FOR_PRICE, valueForPriceEntries8));

        pub8.setRatings(new Ratings(ratings8));


        pub8.setUrl("https://i2-prod.chroniclelive.co.uk/incoming/article15275207.ece/ALTERNATES/s1227b/5_IBP_NEC_121018zerox_05JPG.jpg");

        pubs.add(pub8);



        // ----------------------------------
        //
        // End of setup for one pub.
        //
        // ----------------------------------


        // Pub 9 - Tokyo Newcastle

        Pub pub9 = new Pub("Tokyo Newcastle",54.9692451,-1.6153896);

        pub9.setID("8");

        //Opening hours

        ArrayList<SingleOpeningHours> singleOpeningHours9 = new ArrayList<SingleOpeningHours>();

        singleOpeningHours9.add(new SingleOpeningHours(Day.MONDAY, "11:00", "00:00"));
        singleOpeningHours9.add(new SingleOpeningHours(Day.TUESDAY, "11:00", "01:00"));
        singleOpeningHours9.add(new SingleOpeningHours(Day.WEDNESDAY, "11:00", "01:15"));
        singleOpeningHours9.add(new SingleOpeningHours(Day.THURSDAY, "11:00","01:00"));
        singleOpeningHours9.add(new SingleOpeningHours(Day.FRIDAY, "11:00", "02:00"));
        singleOpeningHours9.add(new SingleOpeningHours(Day.SATURDAY, "11:00", "02:00"));
        singleOpeningHours9.add(new SingleOpeningHours(Day.SUNDAY, "11:00", "01:00"));

        //Prices

        // (Brand.FOSTERS, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.BIRRA_MORRETI, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.HEINEKEN, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.PERONI, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.STELLA_ARTOIS, DrinkType.BEER,Amount.PINT));

        ArrayList<Price> singlePrices9 = new ArrayList<Price>();

        singlePrices9.add(new Price(productSetup.products.get(0),3.95));
        singlePrices9.add(new Price(productSetup.products.get(1),4.7));
        singlePrices9.add(new Price(productSetup.products.get(2),3.75));
        singlePrices9.add(new Price(productSetup.products.get(3),4.5));
        singlePrices9.add(new Price(productSetup.products.get(4),4.15));

        WeekOpeningHours weekOpeningHours9 = new WeekOpeningHours(singleOpeningHours9);

        pub9.setWeekOpeningHours(weekOpeningHours9);
        pub9.setPrices(new Prices(singlePrices9));

        //Facilities;

        ArrayList<Facility>  facilityInitList9 = new ArrayList<Facility>();
        facilityInitList9.add(new Facility(FacilityType.CAR_PARKING,"Car Parking"));
        facilityInitList9.add(new Facility(FacilityType.LIVE_SPORTS,"Live Sports"));

        pub9.setFacilities(new Facilities(facilityInitList9));

        // Ratings;

        ArrayList<Rating> ratings9 = new ArrayList<>();

        ArrayList<RatingEntry> atmosphereEntries9= new ArrayList<>();

        atmosphereEntries9.add(new RatingEntry(RatingType.ATMOSPHERE,4.5));

        ratings9.add(new Rating(RatingType.ATMOSPHERE, atmosphereEntries9));


        ArrayList<RatingEntry> serviceEntries9 = new ArrayList<>();

        serviceEntries9.add(new RatingEntry(RatingType.SERVICE,3.5));

        ratings9.add(new Rating(RatingType.SERVICE, serviceEntries9));


        ArrayList<RatingEntry> hygieneEntries9 = new ArrayList<>();

        hygieneEntries9.add(new RatingEntry(RatingType.HYGIENE,3.5));

        ratings9.add(new Rating(RatingType.HYGIENE, hygieneEntries9));


        ArrayList<RatingEntry> valueForPriceEntries9 = new ArrayList<>();

        valueForPriceEntries9.add(new RatingEntry(RatingType.VALUE_FOR_PRICE,2));

        ratings9.add(new Rating(RatingType.VALUE_FOR_PRICE, valueForPriceEntries9));

        pub9.setRatings(new Ratings(ratings9));


        pub9.setUrl("https://i2.wp.com/unifresher.co.uk/wp-content/uploads/2018/06/tokyo-bar.jpg?resize=1024%2C682&ssl=1");

        pubs.add(pub9);


        // ----------------------------------
        //
        // End of setup for one pub.
        //
        // ----------------------------------


        // Pub 10 - The Forth

        Pub pub10 = new Pub("The Forth",54.9695968,-1.6205731);

        pub10.setID("9");

        //Opening hours

        ArrayList<SingleOpeningHours> singleOpeningHours10 = new ArrayList<SingleOpeningHours>();

        singleOpeningHours10.add(new SingleOpeningHours(Day.MONDAY, "11:00", "00:00"));
        singleOpeningHours10.add(new SingleOpeningHours(Day.TUESDAY, "11:00", "01:00"));
        singleOpeningHours10.add(new SingleOpeningHours(Day.WEDNESDAY, "11:00", "02:00"));
        singleOpeningHours10.add(new SingleOpeningHours(Day.THURSDAY, "11:00","01:00"));
        singleOpeningHours10.add(new SingleOpeningHours(Day.FRIDAY, "11:00", "02:00"));
        singleOpeningHours10.add(new SingleOpeningHours(Day.SATURDAY, "11:00", "02:00"));
        singleOpeningHours10.add(new SingleOpeningHours(Day.SUNDAY, "11:00", "01:00"));

        //Prices

        // (Brand.FOSTERS, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.BIRRA_MORRETI, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.HEINEKEN, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.PERONI, DrinkType.BEER,Amount.PINT));
        // products.add(new Product(Brand.STELLA_ARTOIS, DrinkType.BEER,Amount.PINT));

        ArrayList<Price> singlePrices10 = new ArrayList<Price>();

        singlePrices10.add(new Price(productSetup.products.get(0),4.00));
        singlePrices10.add(new Price(productSetup.products.get(1),4.95));
        singlePrices10.add(new Price(productSetup.products.get(2),4.25));
        singlePrices10.add(new Price(productSetup.products.get(3),4.45));
        singlePrices10.add(new Price(productSetup.products.get(4),4.50));

        WeekOpeningHours weekOpeningHours10 = new WeekOpeningHours(singleOpeningHours10);

        pub10.setWeekOpeningHours(weekOpeningHours10);
        pub10.setPrices(new Prices(singlePrices10));

        //Facilities;

        ArrayList<Facility>  facilityInitList10 = new ArrayList<Facility>();
        facilityInitList10.add(new Facility(FacilityType.CAR_PARKING,"Car Parking"));
        facilityInitList10.add(new Facility(FacilityType.LIVE_SPORTS,"Live Sports"));

        pub10.setFacilities(new Facilities(facilityInitList9));

        // Ratings;

        ArrayList<Rating> ratings10 = new ArrayList<>();

        ArrayList<RatingEntry> atmosphereEntries10= new ArrayList<>();

        atmosphereEntries10.add(new RatingEntry(RatingType.ATMOSPHERE,4.5));

        ratings10.add(new Rating(RatingType.ATMOSPHERE, atmosphereEntries10));


        ArrayList<RatingEntry> serviceEntries10 = new ArrayList<>();

        serviceEntries10.add(new RatingEntry(RatingType.SERVICE,4.5));

        ratings10.add(new Rating(RatingType.SERVICE, serviceEntries10));


        ArrayList<RatingEntry> hygieneEntries10 = new ArrayList<>();

        hygieneEntries10.add(new RatingEntry(RatingType.HYGIENE,4.5));

        ratings10.add(new Rating(RatingType.HYGIENE, hygieneEntries10));


        ArrayList<RatingEntry> valueForPriceEntries10 = new ArrayList<>();

        valueForPriceEntries10.add(new RatingEntry(RatingType.VALUE_FOR_PRICE,4.5));

        ratings10.add(new Rating(RatingType.VALUE_FOR_PRICE, valueForPriceEntries10));

        pub10.setRatings(new Ratings(ratings10));


        pub10.setUrl("https://media-cdn.tripadvisor.com/media/photo-s/04/52/f1/b1/the-forth.jpg");

        pubs.add(pub10);

       // myRef.child("list").setValue(pubs);

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


    public Pub returnPubByName(String name){

        for(Pub p : pubs){
            if(name.equals(p.name)){
                return p;
            }
        }

        return new Pub();




    }









}
