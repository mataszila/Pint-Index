package com.example.matasolutions.pintindex;

import java.util.ArrayList;

class Prices implements PubPageContentInterface{

    ArrayList<Price> priceList;

    public Prices(){

        priceList  = new ArrayList<Price>();

    }

    @Override
    public String ContentToString() {
        return "Prices not available";
    }

}
