package com.example.matasolutions.pintindex;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class PubActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Pub pub;

    TextView toolbar_text_1;
    TextView toolbar_text_2;
    TextView toolbar_text_4;

    CardView toolbar_card_1;
    CardView rate_card;
    CardView compare_with_card;

    ArrayList<ImageView> facilityLogos;

    RecyclerView toolbar_recyclerview;
    LinearLayoutManager layoutManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub);

        setupPub();
        SetupToolbar();

        AddPubPageContent();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = pub.coordinates;
        mMap.addMarker(new MarkerOptions().position(sydney).title(pub.name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(pub.coordinates.latitude,pub.coordinates.longitude), 15.0f));

    }

    private void setupPub(){

        pub = new Pub();

        Bundle bundle = getIntent().getParcelableExtra("bundle");
        pub.coordinates = bundle.getParcelable("coordinates");
        pub.name = (String) getIntent().getSerializableExtra("name");

        pub.coordinates = bundle.getParcelable("coordinates");
        //marker;

        pub.weekOpeningHours = new WeekOpeningHours((ArrayList<SingleOpeningHours>) getIntent().getSerializableExtra("workingHoursList"));
        pub.prices = new Prices((ArrayList<Price>) getIntent().getSerializableExtra("pricesList"));
        pub.facilities = new Facilities((ArrayList<Facility>) getIntent().getSerializableExtra("facilitiesList"));
        pub.ratings = new Ratings((ArrayList<Rating>) getIntent().getSerializableExtra("ratingsList"));

        setTitle(pub.name);

    }

    private void SetupToolbar(){

        toolbar_text_1 = findViewById(R.id.toolbar_text_1);
        toolbar_text_1.setText(SetPubOpeningStatus());

        toolbar_text_2 = findViewById(R.id.toolbar_text_2);
        toolbar_text_2.setText(ShowRatingText());


        toolbar_text_4 = findViewById(R.id.toolbar_text_4);

        toolbar_card_1 = findViewById(R.id.toolbar_card_1);
        compare_with_card = findViewById(R.id.toolbar_card_4);

        rate_card = findViewById(R.id.toolbar_card_3);

        rate_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),RateActivity.class);
                startActivity(intent);
            }
        });


        toolbar_text_4.setText("COMPARE WITH...");

        compare_with_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),PubCompareActivity.class);
                    intent.putExtra("pubName", pub.name);
                    startActivity(intent);
            }
        });





        SetupFacilityLogos();

        //SetupRecyclerView();

    }

    private void SetupRecyclerView(){

        layoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);

        toolbar_recyclerview = findViewById(R.id.toolbar_recyclerview);

        toolbar_recyclerview.setLayoutManager(layoutManager);
        RecyclerView.Adapter mAdapter = new MyAdapter(pub.facilities.facilities);
        toolbar_recyclerview.setAdapter(mAdapter);

    }


    private void SetupFacilityLogos(){

        facilityLogos = new ArrayList<ImageView>();
        for(int i=0;i<pub.facilities.facilities.size();i++){

            Facility thisFacility = pub.facilities.facilities.get(i);
            ImageView imageView = new ImageView(this);

            imageView.setImageResource(PubSetup.ReturnResourceID(thisFacility));

            facilityLogos.add(imageView);
        }

    }




    private String ShowRatingText(){
        return pub.ratings.averageRating + "/5";
    }


    private SpannableString SetPubOpeningStatus(){

        SingleOpeningHours hoursForToday  = pub.weekOpeningHours.openingHours.get(HelperMethods.GetCorrectDayOfWeek()-1);


        try {
            if(HelperMethods.isPubOpen(hoursForToday.openingTime,hoursForToday.closingTime ,HelperMethods.getHoursMinutesNow() )){


                return FormatStatusText("OPEN NOW", "UNTIL ", hoursForToday.closingTime);
            }
            else{
                return FormatStatusText("CLOSED", "OPENS ", hoursForToday.openingTime);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new SpannableString("N/A");

    }

    private SpannableString FormatStatusText(String status, String action, String actionTime) {


        StringBuilder sb = new StringBuilder();

        for(char c : actionTime.toCharArray()){

            if(c != ':'){
                sb.append(c);
            }


        }

        String prep = status + " (" + action + actionTime + ")";

        SpannableString ans = new SpannableString(prep);
        ans.setSpan(new ForegroundColorSpan(Color.rgb(0,139,0)), status.length(), ans.length(), 0);

        return ans;

    }



    private void AddPubPageContent() {

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        ArrayList<PubPageContentParent> parentList = new ArrayList<PubPageContentParent>();

        //WeekOpeningHours

        ArrayList<PubPageContentChild> openingHoursChild = new ArrayList<PubPageContentChild>();

        openingHoursChild.add(new PubPageContentChild(pub.weekOpeningHours.ContentToString()));

        parentList.add(new PubPageContentParent("Opening Hours", openingHoursChild));

        //Prices

        ArrayList<PubPageContentChild> pricesChild = new ArrayList<PubPageContentChild>();

        pricesChild.add(new PubPageContentChild(pub.prices.ContentToString()));

        parentList.add(new PubPageContentParent("Prices", pricesChild));

        //Facilities

        ArrayList<PubPageContentChild> facilitiesChild = new ArrayList<PubPageContentChild>();

        for(int i=0;i<pub.facilities.facilities.size();i++){

            Facility current = pub.facilities.facilities.get(i);

            facilitiesChild.add(new PubPageContentChild(current.name,current.category,current));

        }

        parentList.add(new PubPageContentParent("Facilities", facilitiesChild));

        //Rating

        ArrayList<PubPageContentChild> ratingsChild = new ArrayList<PubPageContentChild>();

        ratingsChild.add(new PubPageContentChild(pub.ratings.ContentToString()));

        parentList.add(new PubPageContentParent("Ratings", ratingsChild));

        //Adapter

        ChildAdapter adapter = new ChildAdapter(parentList);
        recyclerView.setAdapter(adapter);

    }


    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private ArrayList<Facility> mDataset;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public ImageView logo;


            public MyViewHolder(View v) {
                super(v);

                logo = v.findViewById(R.id.facilityLogo);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<Facility> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            View v = inflater.inflate(R.layout.facility_logo, parent, false);

            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            ImageView logo = holder.logo;

            // Set item views based on your views and data model
            logo.setImageResource(new PubSetup().ReturnResourceID(mDataset.get(position)));


        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }


}

