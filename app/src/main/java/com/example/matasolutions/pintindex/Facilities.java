package com.example.matasolutions.pintindex;

import java.io.Serializable;
import java.util.ArrayList;

class Facilities  {

    public ArrayList<Facility> facilities;
    public final PubPageCategory category = PubPageCategory.FACILITIES;



    public Facilities(ArrayList<Facility> facilities){

        this.facilities = facilities;

    }

}
