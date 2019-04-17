package com.example.matasolutions.pintindex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    PubSetup setup;
    GPSTracker tracker;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;




    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setTitle("Search");


        setup = getIntent().getParcelableExtra("pubSetup");
        tracker = new GPSTracker(getApplicationContext());

        SetupSearch();
        SetupRecyclerView();

    }


    private void SetupSearch(){

        String[] fruits = {"Apple", "Banana", "Cherry", "Date", "Grape", "Kiwi", "Mango", "Pear"};

        ArrayList<String> pubNames = new ArrayList<>();

        for(Pub pub : setup.pubs){

            pubNames.add(pub.name);

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, pubNames);
        //Getting the instance of AutoCompleteTextView
        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.RED);

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

    private void SetupRecyclerView(){

        recyclerView = findViewById(R.id.search_recyclerView);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyAdapter(setup.pubs,tracker);
        recyclerView.setAdapter(mAdapter);



    }

    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private ArrayList<Pub> mDataset;
        private GPSTracker tracker;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder

        public static class MyViewHolder extends RecyclerView.ViewHolder {

            public ImageView pub_image;
            public TextView pub_name;
            public TextView pub_rating;
            public TextView pub_facilities;
            public TextView pub_distance;

            public MyViewHolder(View v) {
                super(v);

                pub_image = (ImageView) v.findViewById(R.id.search_pub_image);
                pub_name = (TextView) v.findViewById(R.id.search_pub_name);
                pub_rating = (TextView) v.findViewById(R.id.search_pub_rating);
                pub_facilities = (TextView) v.findViewById(R.id.search_pub_facilities);
                pub_distance = (TextView) v.findViewById(R.id.search_pub_distance);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<Pub> myDataset, GPSTracker tracker) {
            mDataset = myDataset;
            this.tracker = tracker;
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
            Pub data = mDataset.get(position);

            // Set item views based on your views and data model

            ImageView pub_image = holder.pub_image;
            TextView pub_name = holder.pub_name;
            TextView pub_rating = holder.pub_rating;
            TextView pub_facilities = holder.pub_facilities;
            TextView pub_distance = holder.pub_distance;


            Picasso.get()
                    .load(data.url)
                    .resize(480, 360)
                    .into(pub_image);


            pub_name.setText(data.name);
            pub_rating.setText(String.valueOf(data.getRatings().globalAverageRating));
            pub_facilities.setText("0");

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
