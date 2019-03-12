package com.example.matasolutions.pintindex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public class PubActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Pub pub;

    String workingHours;
    String prices;
    String ratings;

    ArrayList<Facility> facilityArrayList;
    ArrayList<Rating> ratingArrayList;
    ArrayList<SingleOpeningHours> openingHoursArrayList;
    Button mGridButton1;
    Button mGridButton2;
    Button mGridButton3;
    Button mGridButton4;


    double averageRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub);

        try {
            setupPub();
            SetupToolbar();
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(pub.coordinates.latitude, pub.coordinates.longitude), 15.0f));

    }

    private void setupPub() throws ParseException {

        pub = new Pub();

        Bundle bundle = getIntent().getParcelableExtra("bundle");
        pub.coordinates = bundle.getParcelable("coordinates");
        pub.name = (String) getIntent().getSerializableExtra("name");
        workingHours = (String) getIntent().getSerializableExtra("workingHours");
        prices = (String) getIntent().getSerializableExtra("prices");
        averageRating = (Double) getIntent().getSerializableExtra("averageRating");

        facilityArrayList = (ArrayList<Facility>) getIntent().getSerializableExtra("facilitiesList");
        ratingArrayList = (ArrayList<Rating>) getIntent().getSerializableExtra("ratingsList");
        openingHoursArrayList = (ArrayList<SingleOpeningHours>) getIntent().getSerializableExtra("workingHoursList");

        ratings = (String) getIntent().getSerializableExtra("ratings");

        setTitle(pub.name);

    }

    private void SetupToolbar() throws ParseException {

        mGridButton1 = findViewById(R.id.grid_rating_button1);
        mGridButton2 = findViewById(R.id.grid_rating_button2);
        mGridButton3 = findViewById(R.id.grid_rating_button3);
        mGridButton4 = findViewById(R.id.grid_rating_button4);

        mGridButton1.setText(CheckIfOpen());


        mGridButton2.setText(String.valueOf(averageRating) + "/5");
        mGridButton4.setText("COMPARE WITH...");

        mGridButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),PubCompareActivity.class);
                intent.putExtra("pubName", pub.name);
                startActivity(intent);

            }
        });

    }


    private void AddPubPageContent() {

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        ArrayList<PubPageContentParent> parentList = new ArrayList<PubPageContentParent>();

        //OpeningHours


        ArrayList<PubPageContentChild> openingHoursChild = new ArrayList<PubPageContentChild>();

        openingHoursChild.add(new PubPageContentChild(workingHours));

        parentList.add(new PubPageContentParent("Opening Hours", openingHoursChild));


        //Prices


        ArrayList<PubPageContentChild> pricesChild = new ArrayList<PubPageContentChild>();

        pricesChild.add(new PubPageContentChild(prices));

        parentList.add(new PubPageContentParent("Prices", pricesChild));


        //Facilities


        ArrayList<PubPageContentChild> facilitiesChild = PubSetup.AddToChildList(facilityArrayList);

        parentList.add(new PubPageContentParent("Facilities", facilitiesChild));


        //Rating

        ArrayList<PubPageContentChild> ratingsChild = new ArrayList<PubPageContentChild>();

        ratingsChild.add(new PubPageContentChild(ratings));

        parentList.add(new PubPageContentParent("Ratings", ratingsChild));

        //Adapter

        ChildAdapter adapter = new ChildAdapter(parentList);
        recyclerView.setAdapter(adapter);

    }

    private String CheckIfOpen() throws ParseException {

        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);

        SingleOpeningHours openingHoursToday = openingHoursArrayList.get(currentDay - 2);


        if (isThePubOpen(FormatHours(openingHoursToday.openingTime), FormatHours(openingHoursToday.closingTime), getCurrentHHmm(calendar))) {
            return "OPEN NOW";
        }
        else{
            return "CLOSED";
        }

    }

    private String FormatHours(String hhmm){
        return hhmm + ":00";

    }

    private String getCurrentHHmm(Calendar calendar){
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        StringBuilder sb = new StringBuilder();
        sb.append(hours);
        sb.append(":");
        sb.append(minutes);

        return FormatHours(sb.toString());
    }

    public static boolean isThePubOpen(String initialTime, String finalTime,
                                       String currentTime) throws ParseException {

            String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
            if (initialTime.matches(reg) && finalTime.matches(reg) &&
                    currentTime.matches(reg))
            {
                boolean valid = false;
                //Start Time
                //all times are from java.util.Date
                Date inTime = new SimpleDateFormat("HH:mm:ss").parse(initialTime);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.setTime(inTime);

                //Current Time
                Date checkTime = new SimpleDateFormat("HH:mm:ss").parse(currentTime);
                Calendar calendar3 = Calendar.getInstance();
                calendar3.setTime(checkTime);

                //End Time
                Date finTime = new SimpleDateFormat("HH:mm:ss").parse(finalTime);
                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(finTime);

                if (finalTime.compareTo(initialTime) < 0)
                {
                    calendar2.add(Calendar.DATE, 1);
                    //calendar3.add(Calendar.DATE, 1);
                }

                java.util.Date actualTime = calendar3.getTime();
                if ((actualTime.after(calendar1.getTime()) ||
                        actualTime.compareTo(calendar1.getTime()) == 0) &&
                        actualTime.before(calendar2.getTime()))
                {
                    valid = true;
                    return valid;
                }
                else{
                    return false;
                }
            }
            else {
                throw new IllegalArgumentException("Not a valid time, expecting HH:MM:SS format");
            }
    }

}

