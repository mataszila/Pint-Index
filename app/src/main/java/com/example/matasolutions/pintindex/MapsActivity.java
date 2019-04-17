package com.example.matasolutions.pintindex;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.pm.PermissionInfoCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;


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

        tracker = new GPSTracker(this);

        //pubSetup = new PubSetup();

        pubSetup = getIntent().getParcelableExtra("pubSetup");

        SetupBottomNavigationView();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void SetupBottomNavigationView(){

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int menuId = menuItem.getItemId();

                switch(menuId){

                    case R.id.navbar_home_item:
                        startActivity(new Intent(getApplicationContext(),MapsActivity.class));
                        break;
                    case R.id.navbar_search_item:
                        Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
                        intent.putExtra("pubSetup",pubSetup);
                        startActivity(intent);
                        break;
                    case R.id.navbar_profile_item:

                        if(profile != null){
                            Intent profileIntent = new Intent(getApplicationContext(),ProfileActivity.class);
                            profileIntent.putExtra("pubSetup",pubSetup);
                            startActivity(profileIntent);
                        }
                        else {
                            Intent startIntent = new Intent(getApplicationContext(),StartActivity.class);
                            startIntent.putExtra("pubSetup",pubSetup);
                            startActivity(startIntent);
                        }
                        break;
                    case R.id.navbar_products_item:

                        Intent productsIntent = new Intent(getApplicationContext(),ProductActivity.class);
                        productsIntent.putExtra("pubSetup",pubSetup);

                        startActivity(productsIntent);
                        break;
                }

                return false;
            }
        });

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