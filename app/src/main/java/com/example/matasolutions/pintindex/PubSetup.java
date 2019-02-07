package com.example.matasolutions.pintindex;

import java.util.ArrayList;
import java.util.List;

public class PubSetup {

    ArrayList<Pub> pubs;

    public PubSetup(){

        pubs = new ArrayList<Pub>();
        AddPubs();

    }

    private void AddPubs(){

        pubs.add(new Pub("Bar Loco",54.9755819,-1.6202004));
        pubs.add(new Pub("Hancock",54.979915,-1.6136037));
        pubs.add(new Pub("The Strawberry",54.9748055,-1.6217146));
        pubs.add(new Pub("Trent House",54.977095,-1.6205557));
        pubs.add(new Pub("Luther's",54.9789707,-1.6172808));

    }

}
