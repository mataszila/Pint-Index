package com.example.matasolutions.pintindex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;

public class ProductActivity extends AppCompatActivity {

    Spinner DrinkTypeSpinner;
    Spinner BrandSpinner;
    Spinner AmountSpinner;

    DrinkType drinkType;
    Brand brand;
    Amount amount;

    ArrayList<Pub> pubsWithProduct;
    String ansText;

    Button actionButton;

    Button sort_high_to_low;
    Button sort_low_to_high;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        DrinkTypeSpinner = findViewById(R.id.DrinkType_Spinner);
        BrandSpinner = findViewById(R.id.Brand_Spinner);
        AmountSpinner = findViewById(R.id.Amount_Spinner);

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


        SetListener(DrinkTypeSpinner, ProductSpinnerType.DRINKTYPE);
        SetListener(BrandSpinner, ProductSpinnerType.BRAND);
        SetListener(AmountSpinner, ProductSpinnerType.AMOUNT);


        actionButton = findViewById(R.id.action_button);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DoEverythingElse();


            }
        });


    }


    private void SetListener(Spinner spinner, final ProductSpinnerType type ){

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (type){

                    case BRAND:
                        brand = (Brand) adapterView.getItemAtPosition(i);
                        break;
                    case AMOUNT:
                        amount = (Amount) adapterView.getItemAtPosition(i);
                        break;
                    case DRINKTYPE:
                        drinkType = (DrinkType) adapterView.getItemAtPosition(i);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    
    private void DoEverythingElse() {

        final Product prod = new Product(brand, drinkType, amount);

        pubsWithProduct = FindPubsWithProduct(prod);

        StringBuilder sb = new StringBuilder();

        sb.append(drinkType.toString() + brand.toString() + amount.toString());

        for(int i=0;i<pubsWithProduct.size();i++){

            Pub thisPub = pubsWithProduct.get(i);

            String val = String.valueOf(findProductinPub(prod, thisPub).price);

            sb.append(thisPub.name + " " + val + " \n");
        }

        ansText = sb.toString();

        recyclerView =  findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyAdapter(prod,pubsWithProduct);
        recyclerView.setAdapter(mAdapter);

        sort_high_to_low = findViewById(R.id.sort_high_to_low);
        sort_low_to_high= findViewById(R.id.sort_low_to_high);

        sort_high_to_low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SortByPrice(true, prod);
                recyclerView.setAdapter(mAdapter);


            }
        });

        sort_low_to_high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortByPrice(false, prod);
                recyclerView.setAdapter(mAdapter);

            }
        });

    }


    public void SortByPrice(final boolean highToLow, final Product prod){

        Collections.sort(pubsWithProduct, new Comparator<Pub>() {
            @Override
            public int compare(Pub lhs, Pub rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending

                double p1 = findProductinPub(prod, lhs).price;
                double p2 = findProductinPub(prod, rhs).price;

                if(highToLow == true){
                    return p1 > p2 ? -1 : (p1 < p2) ? 1 : 0;

                }

                return p1 > p2 ? 1 : (p1 < p2) ? -1 : 0;
            }
        });


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



    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private ArrayList<Pub> mDataset;
        private Product product;


        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case

            public TextView place;
            public TextView brand;
            public TextView price;


            public MyViewHolder(View v) {
                super(v);

                place = (TextView) v.findViewById(R.id.place);
                brand = (TextView) v.findViewById(R.id.brand);
                price = (TextView) v.findViewById(R.id.price);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(Product prod,ArrayList<Pub> myDataset) {
            mDataset = myDataset;
            product = prod;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View v = inflater.inflate(R.layout.productprice_recyclerview, parent, false);

            MyAdapter.MyViewHolder vh = new MyAdapter.MyViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            Pub thisPub = mDataset.get(position);

            // Set item views based on your views and data model
            TextView place = holder.place;
            TextView brand = holder.brand;
            TextView price  = holder.price;

            place.setText(String.valueOf(position+1));
            brand.setText(thisPub.name);
            price.setText(String.valueOf(HelperMethods.LookupProductPrice(product, thisPub)));


        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }









}
