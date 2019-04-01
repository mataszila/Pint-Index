package com.example.matasolutions.pintindex;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    public Brand brand;
    public DrinkType type;
    public Amount amount;

    public Product(Brand brand, DrinkType type, Amount amount){
        this.brand = brand;
        this.type = type;
        this.amount = amount;
    }

    protected Product(Parcel in) {
        brand = (Brand) in.readValue(Brand.class.getClassLoader());
        type = (DrinkType) in.readValue(DrinkType.class.getClassLoader());
        amount = (Amount) in.readValue(Amount.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(brand);
        dest.writeValue(type);
        dest.writeValue(amount);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}


