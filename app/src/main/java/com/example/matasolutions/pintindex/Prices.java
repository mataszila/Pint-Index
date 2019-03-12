package com.example.matasolutions.pintindex;

import java.util.ArrayList;

class Prices implements PubPageContentInterface{

    ArrayList<Price> priceList;
    public final PubPageCategory category = PubPageCategory.PRICES;



    public Prices(ArrayList<Price> priceList){

        this.priceList  =  priceList;
    }

    public Prices() {
        priceList = new ArrayList<Price>();
    }

    @Override
    public String ContentToString() {

        StringBuilder sb = new StringBuilder();

        for(int i = 0;i<priceList.size();i++){

            Price current = priceList.get(i);


            sb.append(current.product.name + " " + current.price + "\n" );







        }



        //sb.append(thisInstance.SingleOpeningHoursToString() + "\n");


        return sb.toString();


    }

}
