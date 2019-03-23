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

    ArrayList<Pub> pubsWithProduct;
    String ansText;

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
                DoEverythingElse();

                textView.setText(ansText );

            }
        });




    }

    private void DoEverythingElse() {

        Product prod = new Product(brand, drinkType, amount);

        pubsWithProduct = FindPubsWithProduct(prod);

        StringBuilder sb = new StringBuilder();

        sb.append(drinkType.toString() + brand.toString() + amount.toString());

        for(int i=0;i<pubsWithProduct.size();i++){

            Pub thisPub = pubsWithProduct.get(i);

            String val = String.valueOf(findProductinPub(prod, thisPub).price);

            sb.append(thisPub.name + " " + val + " \n");
        }

        ansText = sb.toString();
    }

    private boolean DoProductsMatch(Product one, Product two){

        if(one.brand == two.brand && one.type == two.type && one.amount == two.amount ){
            return true;
        }
        return false;

    }


    private ArrayList<Pub> FindPubsWithProduct(Product prod){

        ArrayList<Pub> list = new ArrayList<Pub>();

        PubSetup setup = new PubSetup();

        for(int i=0;i<setup.pubs.size();i++){

            Pub thisPub = setup.pubs.get(i);

            for(int j=0;j<thisPub.prices.priceList.size();j++){

                Product thisProduct = thisPub.prices.priceList.get(j).product;

                if(DoProductsMatch(prod, thisProduct)){

                    list.add(thisPub);
                }

            }

        }

        return list;

    }

    private Price findProductinPub(Product prod, Pub pub){

        Price ans = null;

        for(Price p : pub.prices.priceList){

            if(DoProductsMatch(p.product,prod)){

                ans = p;

            }

        }
        return ans;
    }




}
