package com.example.matasolutions.pintindex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class SearchActivity extends AppCompatActivity {

    PubSetup setup;
    GPSTracker tracker;

    MaterialSpinner sortBySpinner;


    ArrayList<String> pubNames;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_search);
        setTitle("");

        setup = getIntent().getParcelableExtra("pubSetup");
        tracker = new GPSTracker(getApplicationContext());

        SetupSearch();

        SetupRecyclerView();

        SetupSpinner();

    }

    private void SetupSpinner(){

        sortBySpinner = findViewById(R.id.search_sortby_spinner);

        ArrayList<String> sortByTypeList = new ArrayList<>();

        sortByTypeList.add("Sort by...");
        sortByTypeList.add("Distance (low to high)");
        sortByTypeList.add("Distance (high to low)");
        sortByTypeList.add("Rating (low to high)");
        sortByTypeList.add("Rating (high to low)");
        sortByTypeList.add("Closing time (soonest to latest)");
        sortByTypeList.add("Closing time (latest to soonest)");

        sortBySpinner.setItems(sortByTypeList);


        sortBySpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                executeSort(item);
            }

        });

    }
    private void executeSort(String selectedSort){

        switch (selectedSort){

            case "Rating (low to high)":
                HelperMethods.SortByRatingOrDistance(setup.pubs,SortByType.RATING_AVERAGE,false,HelperMethods.convertLatLng(tracker.getCurrentLatLng()));
                recyclerView.setAdapter(mAdapter);
                break;
            case "Rating (high to low)":
                HelperMethods.SortByRatingOrDistance(setup.pubs,SortByType.RATING_AVERAGE,true,HelperMethods.convertLatLng(tracker.getCurrentLatLng()));
                recyclerView.setAdapter(mAdapter);
                break;

            case "Distance (low to high)":
                HelperMethods.SortByRatingOrDistance(setup.pubs,SortByType.DISTANCE,false,HelperMethods.convertLatLng(tracker.getCurrentLatLng()));
                recyclerView.setAdapter(mAdapter);
                break;
            case "Distance (high to low)":
                HelperMethods.SortByRatingOrDistance(setup.pubs,SortByType.DISTANCE,true,HelperMethods.convertLatLng(tracker.getCurrentLatLng()));
                recyclerView.setAdapter(mAdapter);
                break;
            case "Closing time (soonest to latest)":
                HelperMethods.SortByClosingTime(setup.pubs,false);
                recyclerView.setAdapter(mAdapter);
                break;
            case "Closing time (latest to soonest)":
                HelperMethods.SortByClosingTime(setup.pubs,true);
                recyclerView.setAdapter(mAdapter);
                break;

        }

    }



    private void SetupSearch(){

        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(R.drawable.ic_baseline_search_24px);

        LayoutInflater inflator = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        View v = inflator.inflate(R.layout.actionbar, null);

        actionBar.setCustomView(v);

        SetupPubNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, pubNames);

        AutoCompleteTextView actv =  v.findViewById(R.id.autoCompleteTextView);
        actv.setThreshold(1); //will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.BLACK);

        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selected = (String) parent.getItemAtPosition(position);

                Pub pub = setup.returnPubByName(selected);

                Intent intent = new Intent(getApplicationContext(),PubActivity.class);

                intent.putExtra("pubID",pub.id);

                startActivity(intent);

            }
        });
    }

    private void SetupPubNames(){

        pubNames = new ArrayList<>();

        for(Pub pub : setup.pubs){

            pubNames.add(pub.name);
        }
    }

    private void SetupRecyclerView(){

        recyclerView = findViewById(R.id.search_recyclerView);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyAdapter(setup.pubs,tracker,this);
        recyclerView.setAdapter(mAdapter);



    }

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private ArrayList<Pub> mDataset;
        private GPSTracker tracker;
        private Context context;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder

        public static class MyViewHolder extends RecyclerView.ViewHolder {

            public ImageView pub_image;
            public TextView pub_name;
            public TextView pub_rating;
            public TextView pub_openstatus;
            public TextView pub_distance;
            public LinearLayout search_recyclerview_layout;

            public MyViewHolder(View v) {
                super(v);

                pub_image =  v.findViewById(R.id.search_pub_image);
                pub_name =  v.findViewById(R.id.search_pub_name);
                pub_rating =  v.findViewById(R.id.search_pub_rating);
                pub_openstatus =  v.findViewById(R.id.search_pub_openstatus);
                pub_distance =  v.findViewById(R.id.search_pub_distance);
                search_recyclerview_layout = v.findViewById(R.id.search_recyclerview_layout);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<Pub> myDataset, GPSTracker tracker, Context context) {
            mDataset = myDataset;
            this.tracker = tracker;
            this.context = context;
        }

        // Create new views (invoked by the layout manager)

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View v = inflater.inflate(R.layout.search_recyclerview, parent, false);

            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            final Pub data = mDataset.get(position);

            // Set item views based on your views and data model

            LinearLayout search_recyclerview_layout = holder.search_recyclerview_layout;
            ImageView pub_image = holder.pub_image;
            TextView pub_name = holder.pub_name;
            TextView pub_rating = holder.pub_rating;
            TextView pub_openstatus = holder.pub_openstatus;
            TextView pub_distance = holder.pub_distance;

            search_recyclerview_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PubActivity.class);
                    intent.putExtra("pubID",data.id);
                    context.startActivity(intent);

                }
            });


            Picasso.get()
                    .load(data.url)
                    .resize(480, 360)
                    .into(pub_image);


            pub_name.setText(data.name);
            pub_rating.setText(String.valueOf(data.getRatings().globalAverageRating));
            pub_openstatus.setText(HelperMethods.SetPubOpeningStatus(data));

            String distance = FormatDistanceText(tracker.getCurrentLatLng(),data.coordinates);
            pub_distance.setText(distance);

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }

        private String FormatDistanceText(LatLng currentLatLng, LatLng pubLatLng){


            Double distance = HelperMethods.CalculationByDistance(
                    HelperMethods.convertLatLng(currentLatLng),
                    HelperMethods.convertLatLng(pubLatLng));;

            double rounded = Math.round(distance * 100.0) / 100.0;


            String sb = String.valueOf(rounded) + " km";

            return sb;

        }

    }


    private static final String[] COUNTRIES = new String[] { "Belgium",
            "France", "France_", "Italy", "Germany", "Spain" };

}
