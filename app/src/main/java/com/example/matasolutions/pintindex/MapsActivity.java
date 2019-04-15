package com.example.matasolutions.pintindex;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.danlew.android.joda.JodaTimeAndroid;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private PubSetup pubSetup;

    private GPSTracker tracker;
    private Profile profile;

    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        profile = new Profile();

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int menuId = menuItem.getItemId();

                switch(menuId){

                    case R.id.navbar_home_item:
                        finish();
                        startActivity(new Intent(getApplicationContext(),MapsActivity.class));
                        break;
                    case R.id.navbar_search_item:
                        break;
                    case R.id.navbar_profile_item:

                        if(profile != null){
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }
                        else {
                            startActivity(new Intent(getApplicationContext(), StartActivity.class));
                        }
                        break;
                    case R.id.navbar_products_item:
                        startActivity(new Intent(getApplicationContext(),ProductActivity.class));
                        break;
                }




                return false;
            }
        });



        tracker = new GPSTracker(this);

        //pubSetup = new PubSetup();

        pubSetup = getIntent().getParcelableExtra("pubSetup");


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }



    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setupMap();
        setupMarkers();
        setupListeners();
    }

    private void setupMap() {
        tracker.updateCurrentLocation();

        if(tracker.checkLocationPermission() && tracker.getCurrentLocation() != null){
            mMap.setMyLocationEnabled(true);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    HelperMethods.convertLatLng(new LatLng(tracker.currentLocation.getLatitude(),tracker.currentLocation.getLongitude())),
                    15.0f));
        }
    }

    private void setupListeners(){

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if(marker.equals(marker)){
                    tracker.updateCurrentLocation();
                    Pub thisPub = pubLookupByMarker(marker);
                    LatLng curLatLng = new LatLng(tracker.getCurrentLocation().getLatitude(),tracker.getCurrentLocation().getLongitude());
                    marker.setTitle(formatMarkerTitle(thisPub.name, HelperMethods.CalculationByDistance(HelperMethods.convertLatLng(curLatLng), HelperMethods
                            .convertLatLng(thisPub.coordinates))));
                }

                return false;
            }
        });


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                tracker.updateCurrentLocation();
                Pub thisPub = pubLookupByMarker(marker);

                Intent intent = new Intent(getApplicationContext(),PubActivity.class);
                intent.putExtra("pubID", thisPub.id);
                startActivity(intent);

            }
        });

    }


    private Pub pubLookupByMarker(Marker marker){

        for(int i=0;i<pubSetup.pubs.size();i++) {

            Pub thisPub = pubSetup.pubs.get(i);

            if (marker.equals(thisPub.marker)) {
                return thisPub;
            }
        }
        return new Pub();
    }




    private void setupMarkers() {

        for(int i=0;i<pubSetup.pubs.size();i++){

            Pub thisPub = pubSetup.pubs.get(i);
            LatLng curLatLng = tracker.getCurrentLocation()  == null
                    ? null
                    : new LatLng(
                    tracker.getCurrentLocation().getLatitude(),
                    tracker.getCurrentLocation().getLongitude());

            String title = formatMarkerTitle(thisPub.name, HelperMethods.CalculationByDistance(HelperMethods.convertLatLng(curLatLng),HelperMethods.convertLatLng(thisPub.coordinates)));

            thisPub.marker = mMap.addMarker(new MarkerOptions().
                    position(HelperMethods.convertLatLng(thisPub.coordinates)).
                    title(title));
        }
    }

    private String formatMarkerTitle(String name, double distance){

        double rounded = Math.round(distance * 100.0) / 100.0;

        String s = tracker.getCurrentLocation() == null ? name : name + " " + String.valueOf(rounded) + "km";
        return s;
    }


}