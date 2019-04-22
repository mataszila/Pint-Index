package com.example.matasolutions.pintindex;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

class Prices implements PubPageContentInterface,Parcelable {

    public ArrayList<Price> priceList;
    public PubPageCategory category = PubPageCategory.PRICES;



    public Prices(ArrayList<Price> priceList){

        this.priceList  =  priceList;
    }


    public Prices() {

    }

    @Override
    public String ContentToString() {

        StringBuilder sb = new StringBuilder();

        if(priceList!= null) {

            for(int i = 0;i<priceList.size();i++){

                Price current = priceList.get(i);

                sb.append(current.product.brand + " " + current.price + "\n" );

            }

            return sb.toString();


        }

            return "Prices not available";

    }

    protected Prices(Parcel in) {
        if (in.readByte() == 0x01) {
            priceList = new ArrayList<Price>();
            in.readList(priceList, Price.class.getClassLoader());
        } else {
            priceList = null;
        }
        category = (PubPageCategory) in.readValue(PubPageCategory.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (priceList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(priceList);
        }
        dest.writeValue(category);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Prices> CREATOR = new Parcelable.Creator<Prices>() {
        @Override
        public Prices createFromParcel(Parcel in) {
            return new Prices(in);
        }

        @Override
        public Prices[] newArray(int size) {
            return new Prices[size];
        }
    };





}
