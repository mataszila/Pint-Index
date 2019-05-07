package com.example.matasolutions.pintindex;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class FacilitiesTest {

    Facilities facilities;


    Pub pub;

    public FacilitiesTest(){

        facilities = new Facilities();
        facilities.facilities = new ArrayList<>();

    }



    @Test
    public void Test_Facilities_CheckIfPassedCorrectly(){

        pub = new Pub();

        pub.setFacilities(facilities);

        assertEquals(facilities,pub.facilities);

    }


    @Test
    public void AssertTrue(){

        assertEquals(true,true);


    }
    @Test
    public void Test_Facilities_CheckReturnImage(){

        Facility carParking = new Facility(FacilityType.CAR_PARKING,"Car Parking");
        Facility liveSports = new Facility(FacilityType.LIVE_SPORTS,"Live Sports");
        Facility foodSnacks =  new Facility(FacilityType.FOOD_SNACKS,"Food and snacks");
        Facility freeWifi = new Facility(FacilityType.FREE_WIFI,"Free Wi-Fi");
        Facility liveMusic = new Facility(FacilityType.LIVE_MUSIC,"Live music");

        assertEquals(
                R.drawable.ic_baseline_local_parking_24px,
                facilities.ReturnResourceID(carParking)
        );

        assertEquals(
                R.drawable.ic_baseline_directions_run_24px,
                facilities.ReturnResourceID(liveSports)
        );
        assertEquals(
                R.drawable.ic_baseline_fastfood_24px,
                facilities.ReturnResourceID(foodSnacks)
        );
        assertEquals(
                R.drawable.ic_baseline_network_wifi_24px,
                facilities.ReturnResourceID(freeWifi)
        );
        assertEquals(R.drawable.ic_baseline_music_note_24px,
                facilities.ReturnResourceID(liveMusic)
        );



    }




}
