package com.example.matasolutions.pintindex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.app.AlertDialog;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class PubCompareActivity extends AppCompatActivity {

    String pub1_name;

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

    LocationListener locationListener;
    LocationManager locationManager;

    private GPSTracker tracker;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_compare);

        SetupAlertDialog();

        tracker = new GPSTracker(this);

    }

    private void SetupImages(){

        pub1_image = findViewById(R.id.pub1_image);
        pub2_image = findViewById(R.id.pub2_image);

        Picasso.get()
                .load(pub1.url)
                .resize(480, 360)
                .centerCrop()
                .into(pub1_image);

        Picasso.get()
                .load(pub2.url)
                .resize(480, 360)
                .centerCrop()
                .into(pub2_image);


    }

    private ArrayList<PubCompareData> SetupCompareData(){

        ArrayList<PubCompareData> data = new ArrayList<PubCompareData>();
        //String.valueOf(pub2.ratings.averageRating)
        //String.valueOf(pub1.ratings.averageRating)
        data.add(new PubCompareData("Rating", String.valueOf(pub1.ratings.averageRating), String.valueOf(pub2.ratings.averageRating)));

        LatLng one = new LatLng(tracker.getCurrentLocation().getLatitude(), tracker.getCurrentLocation().getLongitude());

        LatLng two = new LatLng(pub1.coordinates.latitude, pub1.coordinates.longitude);
        LatLng three = new LatLng(pub2.coordinates.latitude, pub2.coordinates.longitude);



        Product product1 = new Product(Brand.STELLA_ARTOIS, DrinkType.BEER,Amount.PINT);

        String price1 = LookupProductPrice(product1, pub1);
        String price2 = LookupProductPrice(product1, pub2);

        Product product2 = new Product(Brand.HEINEKEN, DrinkType.BEER,Amount.PINT);

        String price3 = LookupProductPrice(product2, pub1);
        String price4 = LookupProductPrice(product2, pub2);


        data.add(new PubCompareData("Distance from user", formatDistanceText(one,two ), formatDistanceText(one,three)));
        data.add(new PubCompareData("Price of Stella Artois (1 pint)", price1,price2));
        data.add(new PubCompareData("Price of Heineken (1 pint)", price3, price4));

        return data;
        //
    }


    private String LookupProductPrice(Product product, Pub pub){

        String ans = "N/A";


        for(Price i : pub.prices.priceList){

            if(i.product.brand == product.brand && i.product.type == product.type && i.product.amount == product.amount){

                ans = String.valueOf(i.price);


            }

        }


        return ans;



    }


    private String formatDistanceText(LatLng one, LatLng two){

        double distance = HelperMethods.CalculationByDistance(one, two);

        double rounded = Math.round(distance * 100.0) / 100.0;

        return String.valueOf(rounded) + "km";

    }


    private void SetupViews(String name) throws IOException {

        PubSetup setup = new PubSetup();

        pub2 = setup.returnPubByName(name);
        pub1_name = getIntent().getStringExtra("pubName");
        pub1 = setup.returnPubByName(pub1_name);

        data = SetupCompareData();


        pub1_name_textview = findViewById(R.id.pub1_name_textview);
        pub2_name_textview = findViewById(R.id.pub2_name_textview);

        pub1_name_textview.setText(pub1_name);
        pub2_name_textview.setText(pub2.name);

    }

    private void SetupRecyclerView(){
        recyclerView =  findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(data);
        recyclerView.setAdapter(mAdapter);
    }



    private void SetupActivity(String pub2name){

        try {
            SetupViews(pub2name);
            SetupImages();
            SetupRecyclerView();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected ArrayAdapter<String> SetupArrayAdapter(){

        PubSetup setup = new PubSetup();

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PubCompareActivity.this, android.R.layout.select_dialog_singlechoice);


        for(int i=0;i<setup.pubs.size();i++){

            arrayAdapter.add(setup.pubs.get(i).name);

        }

        return arrayAdapter;

    }


    public synchronized void SetupAlertDialog(){

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
                String strName = arrayAdapter.getItem(which);
                    SetupActivity(strName);



                SetupImages();

                AlertDialog.Builder builderInner = new AlertDialog.Builder(PubCompareActivity.this);
                builderInner.setMessage(strName);
                builderInner.setTitle("Your Selected Item is");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {

                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();


    }

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private ArrayList<PubCompareData> mDataset;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case


            public TextView criteria;
            public TextView card_left;
            public TextView card_right;


            public MyViewHolder(View v) {
                super(v);

                criteria = (TextView) v.findViewById(R.id.pub_compare_text_criteria);
                card_left = (TextView) v.findViewById(R.id.pub_compare_text_left);
                card_right = (TextView) v.findViewById(R.id.pub_compare_text_right);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<PubCompareData> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
            // create a new view
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
            TextView left = holder.card_left;
            TextView right = holder.card_right;

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
