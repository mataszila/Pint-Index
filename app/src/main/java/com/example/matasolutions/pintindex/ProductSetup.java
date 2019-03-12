package com.example.matasolutions.pintindex;

import java.util.ArrayList;

public class ProductSetup {

    public ArrayList<Product> products;

    public ProductSetup(){

        products = new ArrayList<Product>();

        products.add(new Product("Heineken", DrinkType.BEER, 0.568));
        products.add(new Product("Stella Artois", DrinkType.BEER,0.568 ));
        products.add(new Product("Newcastle Brown Ale",DrinkType.BEER,0.568));
        products.add(new Product("Carling",DrinkType.BEER,0.568));



    }




}
