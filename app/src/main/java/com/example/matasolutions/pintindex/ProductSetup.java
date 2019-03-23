package com.example.matasolutions.pintindex;

import java.util.ArrayList;

public class ProductSetup {

    public ArrayList<Product> products;

    public ProductSetup(){

        products = new ArrayList<Product>();

        products.add(new Product(Brand.FOSTERS, DrinkType.BEER,Amount.PINT));
        products.add(new Product(Brand.BIRRA_MORRETI, DrinkType.BEER,Amount.PINT));
        products.add(new Product(Brand.HEINEKEN, DrinkType.BEER,Amount.PINT));
        products.add(new Product(Brand.PERONI, DrinkType.BEER,Amount.PINT));
        products.add(new Product(Brand.STELLA_ARTOIS, DrinkType.BEER,Amount.PINT));

    }





}

