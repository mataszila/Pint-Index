package com.example.matasolutions.pintindex;

import com.google.firebase.database.DatabaseReference;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.constraintlayout.solver.widgets.Helper;

import static org.junit.Assert.*;

public class SearchActivity_Sort_Test {

    private LatLng currentLocation;

    private ArrayList<Pub> distance_Sort_Pubs;

    private ArrayList<Pub> startingList;


    public SearchActivity_Sort_Test(){

        //@54.9710315,-1.6270556,19.5z

        currentLocation = new LatLng(54.9713695,-1.6266533);

        startingList = new ArrayList<>();

        distance_Sort_Pubs = new ArrayList<>();



        Pub pub1 = new Pub("Bar Loco",54.9755819,-1.6202004);
        Pub pub2 = new Pub("Hancock",54.979915,-1.6136037);
        Pub pub3 = new Pub("The Strawberry",54.9748055,-1.6217146);
        Pub pub4 = new Pub("Trent House",54.977095,-1.6205557);
        Pub pub5 = new Pub("Luther's",54.9789707,-1.6172808);
        Pub pub6 = new Pub("Hotspur",54.97709,-1.6172267);
        Pub pub7 = new Pub("Alvino's",54.9720298,-1.6127201);
        Pub pub8 = new Pub("Zerox",54.9688825,-1.6144045);
        Pub pub9 = new Pub("Tokyo Newcastle",54.9692451,-1.6153896);
        Pub pub10 = new Pub("The Forth",54.9695968,-1.6205731);

        startingList.add(pub1);
        startingList.add(pub2);
        startingList.add(pub3);
        startingList.add(pub4);
        startingList.add(pub5);
        startingList.add(pub6);
        startingList.add(pub7);
        startingList.add(pub8);
        startingList.add(pub9);
        startingList.add(pub10);


        distance_Sort_Pubs.add(pub10);// The Forth
        distance_Sort_Pubs.add(pub3); // The Strawberry
        distance_Sort_Pubs.add(pub1); // Bar Loco
        distance_Sort_Pubs.add(pub4); // Trent House
        distance_Sort_Pubs.add(pub9); // Tokyo Newcastle
        distance_Sort_Pubs.add(pub8); // Zerox
        distance_Sort_Pubs.add(pub6); // Hotspur
        distance_Sort_Pubs.add(pub7); // Alvino's
        distance_Sort_Pubs.add(pub5); // Luther's
        distance_Sort_Pubs.add(pub2); // Hancock



    }

    @Test
    public void Test_SearchActivity_CheckDistanceSort(){

        ArrayList<Pub> expectedList = distance_Sort_Pubs;
        ArrayList<Pub> compareList = HelperMethods.SortByRatingOrDistance(
                startingList,
                    SortByType.DISTANCE,
                false,
                HelperMethods.convertLatLng(currentLocation));

        assertEquals(expectedList,compareList);







    }


}
