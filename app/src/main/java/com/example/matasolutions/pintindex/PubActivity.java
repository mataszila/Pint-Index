package com.example.matasolutions.pintindex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;

public class PubActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Pub pub;

    String workingHours;
    String prices;
    String facilities;
    String ratings;

    ArrayList<Facility> facilityArrayList;

    CardView toolbar_card_1;
    TextView toolbar_text_1;

    CardView toolbar_card_2;
    CardView toolbar_card_3;
    CardView toolbar_card_4;




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

        ratings = (String) getIntent().getSerializableExtra("ratings");

        pub.coordinates = bundle.getParcelable("coordinates");
        //marker;

        pub.weekOpeningHours = new WeekOpeningHours((ArrayList<SingleOpeningHours>) getIntent().getSerializableExtra("workingHoursList"));
        pub.prices = new Prices((ArrayList<Price>) getIntent().getSerializableExtra("pricesList"));
        pub.facilities = new Facilities((ArrayList<Facility>) getIntent().getSerializableExtra("facilitiesList"));
        pub.ratings = new Ratings((ArrayList<Rating>) getIntent().getSerializableExtra("ratingsList"));

        setTitle(pub.name);

    }

    private void SetupToolbar(){

        toolbar_card_1 = findViewById(R.id.toolbar_card_1);
        toolbar_text_1 = findViewById(R.id.toolbar_text_1);
        toolbar_text_1.setText("OPEN NOW");

    }

    private boolean CheckPubOpen(){

        for(int i=0;i<pub.weekOpeningHours.openingHours.size();i++){
            

        }





    }



    private void AddPubPageContent() {

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        ArrayList<PubPageContentParent> parentList = new ArrayList<PubPageContentParent>();

        //WeekOpeningHours

        ArrayList<PubPageContentChild> openingHoursChild = new ArrayList<PubPageContentChild>();

        openingHoursChild.add(new PubPageContentChild(workingHours));

        parentList.add(new PubPageContentParent("Opening Hours", openingHoursChild));

        //Prices

        ArrayList<PubPageContentChild> pricesChild = new ArrayList<PubPageContentChild>();

        pricesChild.add(new PubPageContentChild(prices));

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

        ratingsChild.add(new PubPageContentChild(ratings));

        parentList.add(new PubPageContentParent("Ratings", ratingsChild));

        //Adapter

        ChildAdapter adapter = new ChildAdapter(parentList);
        recyclerView.setAdapter(adapter);

    }


}

