package com.example.matasolutions.pintindex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Set;

public class ProductActivity extends AppCompatActivity {

    MaterialSpinner drinkTypeSpinner;
    MaterialSpinner brandSpinner;
    MaterialSpinner amountSpinner;

    private BottomNavigationView bottomNavigationView;


    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    DrinkType drinkType;
    Brand brand;
    Amount amount;

    ArrayList<Pub> pubsWithProduct;
    LinearLayout recyclerViewHeader;
    Button actionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        setTitle("Product price comparison");

        bottomNavigationView = findViewById(R.id.product_bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                final Product prod = new Product(brand, drinkType, amount);

                if(isProductInitialized(prod)) {

                    int menuId = menuItem.getItemId();

                    switch (menuId) {

                        case R.id.navbar_sort_up_item:
                            SortByPrice(false, prod);
                            break;
                        case R.id.navbar_sort_down_item:
                            SortByPrice(true, prod);
                            break;
                    }
                }

                else{
                    Toast.makeText(getApplicationContext(),"Please select your product first!",Toast.LENGTH_SHORT);
                }


                return false;



            }
        });

        SetupSpinners();


        actionButton = findViewById(R.id.action_button);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SearchButtonClicked();


            }
        });

        final Product prod = new Product(brand, drinkType, amount);

        SetupRecyclerView(prod);

    }

    private boolean isProductInitialized(Product prod){

        if(prod.amount != null && prod.brand != null && prod.type != null) {

            return true;
        }

        return false;

    }


    private void SetupSpinners(){

        drinkTypeSpinner = findViewById(R.id.DrinkType_Spinner);
        brandSpinner = findViewById(R.id.Brand_Spinner);
        amountSpinner = findViewById(R.id.Amount_Spinner);

        ArrayList<DrinkType> drinkTypeList = new ArrayList<DrinkType>(EnumSet.allOf(DrinkType.class));
        ArrayList<Brand> brandList = new ArrayList<Brand>(EnumSet.allOf(Brand.class));
        ArrayList<Amount> amountList = new ArrayList<Amount>(EnumSet.allOf(Amount.class));

        drinkTypeSpinner.setItems(drinkTypeList);
        brandSpinner.setItems(brandList);
        amountSpinner.setItems(amountList);

        SetSpinnerListener(drinkTypeSpinner, ProductSpinnerType.DRINKTYPE);
        SetSpinnerListener(brandSpinner, ProductSpinnerType.BRAND);
        SetSpinnerListener(amountSpinner, ProductSpinnerType.AMOUNT);

        drinkType = drinkTypeList.get(0);
        brand = brandList.get(0);
        amount = amountList.get(0);

    }


    private void SetSpinnerListener(MaterialSpinner spinner, final ProductSpinnerType type ){

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {


                switch (type){

                    case BRAND:
                        brand = (Brand) item;
                        break;
                    case AMOUNT:
                        amount = (Amount) item;
                        break;
                    case DRINKTYPE:
                        drinkType = (DrinkType) item;
                        break;
                }

            }

        });


    }


    private void SearchButtonClicked() {

        final Product prod = new Product(brand, drinkType, amount);

        SetupRecyclerView(prod);

   }

    private void SetupRecyclerView(Product prod){

        pubsWithProduct = HelperMethods.FindPubsWithProduct(prod);

        recyclerViewHeader=  findViewById(R.id.product_recycler_view_header);

        recyclerView =  findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyAdapter(prod,pubsWithProduct,getApplicationContext());
        recyclerView.setAdapter(mAdapter);

        recyclerViewHeader.setVisibility(View.VISIBLE);


    }


    public void SortByPrice(final boolean highToLow, final Product prod){

        Collections.sort(pubsWithProduct, new Comparator<Pub>() {
            @Override
            public int compare(Pub lhs, Pub rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending

                double p1 = HelperMethods.FindProductinPub(prod, lhs).price;
                double p2 = HelperMethods.FindProductinPub(prod, rhs).price;

                if(highToLow){
                    return Double.compare(p2, p1);

                }

                return Double.compare(p1, p2);
            }
        });

        recyclerView.setAdapter(mAdapter);


    }


    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private ArrayList<Pub> mDataset;
        private Product product;
        private Context context;


        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case

            public TextView place;
            public TextView brand;
            public TextView price;
            public LinearLayout layout;



            public MyViewHolder(View v) {
                super(v);

                place =  v.findViewById(R.id.place);
                brand =  v.findViewById(R.id.brand);
                price =  v.findViewById(R.id.price);
                layout = v.findViewById(R.id.productprice_recyclerview_layout);

            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(Product prod,ArrayList<Pub> myDataset, Context context) {
            mDataset = myDataset;
            product = prod;
            this.context = context;
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
            final Pub thisPub = mDataset.get(position);

            // Set item views based on your views and data model
            TextView place = holder.place;
            TextView brand = holder.brand;
            TextView price  = holder.price;
            LinearLayout layout = holder.layout;

            place.setText(String.valueOf(position+1));
            brand.setText(thisPub.name);
            price.setText(String.valueOf(HelperMethods.LookupProductPrice(product, thisPub)));

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(context,PubActivity.class);
                    intent.putExtra("pubID",thisPub.id);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);

                }
            });


        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }


}
