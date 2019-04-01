package com.example.matasolutions.pintindex;

import android.os.Parcel;
import android.os.Parcelable;

public class Price implements Parcelable {

    public Product product;
    public double price;

    public Price(Product product, double price){

        this.product = product;
        this.price = price;

    }



    protected Price(Parcel in) {
        product = (Product) in.readValue(Product.class.getClassLoader());
        price = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(product);
        dest.writeDouble(price);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Price> CREATOR = new Parcelable.Creator<Price>() {
        @Override
        public Price createFromParcel(Parcel in) {
            return new Price(in);
        }

        @Override
        public Price[] newArray(int size) {
            return new Price[size];
        }
    };
}




