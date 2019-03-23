package com.example.matasolutions.pintindex;

public class Product {

    public Brand brand;
    public DrinkType type;
    public Amount amount;

    public Product(Brand brand, DrinkType type, Amount amount){
        this.brand = brand;
        this.type = type;
        this.amount = amount;
    }

}
