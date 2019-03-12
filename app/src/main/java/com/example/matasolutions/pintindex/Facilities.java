package com.example.matasolutions.pintindex;

import java.io.Serializable;
import java.util.ArrayList;

class Facilities implements PubPageContentInterface {

    public ArrayList<Facility> facilities;


    public Facilities(ArrayList<Facility> facilities){

        this.facilities = facilities;


    }

    @Override
    public String ContentToString() {

        if(facilities == null){

            return "Facilities not available";

        }


        StringBuilder sb = new StringBuilder();

        for(int i=0;i<facilities.size();i++){
            Facility current = facilities.get(i);


            sb.append(current.name + "\n");

        }

        return sb.toString();

   }
}
