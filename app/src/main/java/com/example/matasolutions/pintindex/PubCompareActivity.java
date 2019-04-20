package com.example.matasolutions.pintindex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.app.AlertDialog;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class PubCompareActivity extends AppCompatActivity {

    String pub1_name;
    String pub2_name;

    Pub pub1;
    Pub pub2;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<PubCompareData> data;

    ImageView pub1_image;
    ImageView pub2_image;

    TextView pub1_name_textview;
    TextView pub2_name_textview;

    private GPSTracker tracker;

    private PubSetup pubSetup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_compare);

        setTitle("Pub comparison");

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffcc00'>Pub Comparison </font>"));

        pubSetup = getIntent().getParcelableExtra("pubSetup");

        SetupAlertDialog();

    }

    public synchronized void SetupAlertDialog(){

        tracker = new GPSTracker(this);

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(PubCompareActivity.this);
        builderSingle.setTitle("Select A Pub:-");

        final ArrayAdapter<String> arrayAdapter = SetupArrayAdapter();

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pub2_name = arrayAdapter.getItem(which);
                SetupActivity(pub2_name);

            }
        });
        builderSingle.show();
    }


    private void SetupActivity(String pub2name){

        try {
            SetupData(pub2name);
            SetupViews();
            SetupImages();
            SetupRecyclerView();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void SetupData(String pub2_name){

        pub2 = pubSetup.returnPubByName(pub2_name);
        pub1_name = getIntent().getStringExtra("pubName");

        pub1 = pubSetup.returnPubByName(pub1_name);

        data = SetupCompareData();
    }


    private void SetupViews() throws IOException {

        pub1_name_textview = findViewById(R.id.pub1_name_textview);
        pub2_name_textview = findViewById(R.id.pub2_name_textview);

        pub1_name_textview.setText(pub1_name);
        pub2_name_textview.setText(pub2.name);

    }

    private void SetupImages(){

        pub1_image = findViewById(R.id.pub1_image);
        pub2_image = findViewById(R.id.pub2_image);

        SetupSingleImage(pub1_image, pub1.url);
        SetupSingleImage(pub2_image, pub2.url);

        }

    private void SetupSingleImage(ImageView imageView, String url){

        Picasso.get()
                .load(url)
                .resize(480, 360)
                .centerCrop()
                .into(imageView);
    }


    private void SetupRecyclerView(){
        recyclerView =  findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyAdapter(data);
        recyclerView.setAdapter(mAdapter);
    }


    private ArrayList<PubCompareData> SetupCompareData(){

        ArrayList<PubCompareData> setup = new ArrayList<PubCompareData>();

        setup.add(new PubCompareData("Rating",
                String.valueOf(pub1.ratings.globalAverageRating),
                String.valueOf(pub2.ratings.globalAverageRating
                )));

        setup.add(
                new PubCompareData("Distance from user",
                        formatDistanceText(pub1),
                        formatDistanceText(pub2)));

        AddMatchingProducts(setup);

        return setup;
    }

    private void AddMatchingProducts(ArrayList<PubCompareData> data){

        ArrayList<Product> matchingList = GetMatchingProducts();

        for(int i = 0; i <matchingList.size();i++ ){

            Product p = matchingList.get(i);

            String p1 = String.valueOf(findProductinPub(p,pub1).price);
            String p2 = String.valueOf(findProductinPub(p,pub2).price);

            data.add(new PubCompareData
                    (FormatProductPriceTitle(p), p1,p2));
        }

    }

    private String FormatProductPriceTitle(Product product){

        StringBuilder sb = new StringBuilder();

        sb.append("Price of ");
        sb.append(GetBrandString(product.brand) + " ");
        sb.append("(" + GetAmountString(product.amount) + ")");

        return sb.toString();
    }


    // enum to String

    private String GetAmountString(Amount amount){

        String ans = "N/A";

        //PINT, HALF_PINT, GLASS, SINGLE, DOUBLE, TRIPLE, UNIT

        switch (amount){
            case PINT:
                ans = "1 pint";
                break;
            case HALF_PINT:
                ans = "half pint";
                break;
            case GLASS:
                ans = "glass";
                break;
            case SINGLE:
                ans = "single";
                break;
            case DOUBLE:
                ans = "double";
                break;
            case TRIPLE:
                ans = "triple";
                break;
            case BOTTLE:
                ans = "bottle";
                break;
        }


        return ans;


    }


    private String GetBrandString(Brand brand){

        String ans = "N/A";

        switch (brand){
            case STELLA_ARTOIS:
                ans = "Stella Artois";
                break;
            case HEINEKEN:
                ans = "Heineken";
                break;
            case CARLSBERG:
                ans = "Carlsberg";
                break;
            case PERONI:
                ans = "Peroni";
                break;
            case BIRRA_MORRETI:
                ans = "Birra Moretti";
                break;
            case JOHN_SMITHS:
                ans = "John Smiths";
                break;
            case FOSTERS:
                ans = "Fosters";
                break;
        }
        return ans;

    }

    // Pub Comparison helpers

    private Price findProductinPub(Product prod, Pub pub){

        Price ans = null;

        for(Price p : pub.prices.priceList){

            if(DoProductsMatch(p.product,prod)){

                ans = p;

            }

        }
        return ans;
    }

    private boolean DoProductsMatch(Product one, Product two){

        if(one.brand == two.brand && one.type == two.type && one.amount == two.amount ){
            return true;
        }
        return false;

    }

    private ArrayList<Product> GetMatchingProducts(){

        ArrayList<Product> productList = new ArrayList<>();

        int pub1_size = pub1.prices.priceList.size();
        int pub2_size = pub2.prices.priceList.size();

        for(int i=0;i<pub1_size;i++){

            for(int j=0;j<pub2_size;j++){

                Product prod1 = pub1.prices.priceList.get(i).product;
                Product prod2 = pub2.prices.priceList.get(j).product;

                if(DoProductsMatch(prod1, prod2)) {

                    productList.add(prod1);
                }
            }
        }

        return productList;
    }


    private String formatDistanceText(Pub one){

        LatLng current = new LatLng(tracker.getCurrentLocation().getLatitude(), tracker.getCurrentLocation().getLongitude());
        LatLng pub = new LatLng(one.coordinates.getLatitude(), one.coordinates.getLongitude());

        double distance = HelperMethods.CalculationByDistance(HelperMethods.convertLatLng(current), HelperMethods.convertLatLng(pub));

        double rounded = Math.round(distance * 100.0) / 100.0;

        return String.valueOf(rounded) + "km";

    }


    protected ArrayAdapter<String> SetupArrayAdapter(){

        PubSetup setup = new PubSetup();

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PubCompareActivity.this, android.R.layout.select_dialog_singlechoice);


        for(int i=0;i<setup.pubs.size();i++){

            arrayAdapter.add(setup.pubs.get(i).name);

        }

        return arrayAdapter;

    }

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private ArrayList<PubCompareData> mDataset;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder

        public static class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView criteria;
            public TextView left_value;
            public TextView right_value;

            public MyViewHolder(View v) {
                super(v);

                criteria = (TextView) v.findViewById(R.id.pub_compare_text_criteria);
                left_value = (TextView) v.findViewById(R.id.pub_compare_text_left);
                right_value = (TextView) v.findViewById(R.id.pub_compare_text_right);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<PubCompareData> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View v = inflater.inflate(R.layout.compare_recyclerview, parent, false);

            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            PubCompareData data = mDataset.get(position);

            // Set item views based on your views and data model
            TextView criteria = holder.criteria;
            TextView left = holder.left_value;
            TextView right = holder.right_value;

            criteria.setText(data.criteria);
            left.setText(data.left_value);
            right.setText(data.right_value);

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }

}