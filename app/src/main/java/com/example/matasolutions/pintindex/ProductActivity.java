package com.example.matasolutions.pintindex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.EnumSet;

public class ProductActivity extends AppCompatActivity {

    Spinner DrinkTypeSpinner;
    Spinner BrandSpinner;
    Spinner AmountSpinner;

    DrinkType drinkType;
    Brand brand;
    Amount amount;

    TextView textView;


    Button actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        DrinkTypeSpinner =findViewById(R.id.DrinkType_Spinner);
        BrandSpinner =findViewById(R.id.Brand_Spinner);
        AmountSpinner =findViewById(R.id.Amount_Spinner);

        ArrayList<DrinkType> drinkTypeList = new ArrayList<DrinkType>(EnumSet.allOf(DrinkType.class));
        ArrayList<Brand> brandList = new ArrayList<Brand>(EnumSet.allOf(Brand.class));
        ArrayList<Amount> amountList = new ArrayList<Amount>(EnumSet.allOf(Amount.class));

        final ArrayAdapter<DrinkType> drinkTypeAdapter = new ArrayAdapter<DrinkType>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,drinkTypeList);
        drinkTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<Brand> brandAdapter = new ArrayAdapter<Brand>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,brandList);
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter<Amount> amountAdapter = new ArrayAdapter<Amount>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,amountList);
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        DrinkTypeSpinner.setAdapter(drinkTypeAdapter);
        BrandSpinner.setAdapter(brandAdapter);
        AmountSpinner.setAdapter(amountAdapter);

        DrinkTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

               drinkType = (DrinkType) adapterView.getItemAtPosition(i);
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });


        BrandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                brand = (Brand) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        AmountSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                amount = (Amount) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        actionButton = findViewById(R.id.action_button);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textView = findViewById(R.id.textview);
                textView.setText(drinkType.toString() + brand.toString() + amount.toString() );

            }
        });


        Product prod = new Product(brand, drinkType, amount);

        PubSetup setup = new PubSetup();



        ArrayList<Pub> pubsWithMatchingProduct = new ArrayList<Pub>();



    }

    private void Find


}
