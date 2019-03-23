package com.example.matasolutions.pintindex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.EnumSet;

public class ProductActivity extends AppCompatActivity {

    Spinner DrinkTypeSpinner;
    Spinner BrandSpinner;
    Spinner AmountSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        DrinkTypeSpinner =findViewById(R.id.DrinkType_Spinner);
        BrandSpinner =findViewById(R.id.Brand_Spinner);
        AmountSpinner =findViewById(R.id.Amount_Spinner);

        ArrayList<DrinkType> drinkTypeList = new ArrayList<DrinkType>(EnumSet.allOf(DrinkType.class));
        ArrayList<Brand> drinkTypeList = new ArrayList<DrinkType>(EnumSet.allOf(DrinkType.class));
        ArrayList<DrinkType> drinkTypeList = new ArrayList<DrinkType>(EnumSet.allOf(DrinkType.class));






    }
}
