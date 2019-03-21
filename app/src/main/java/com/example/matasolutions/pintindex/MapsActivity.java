package com.example.matasolutions.pintindex;

import androidx.annotation.NonNull;
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

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import net.danlew.android.joda.JodaTimeAndroid;

import java.io.Serializable;
import java.text.DecimalFormat;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private PubSetup pubSetup;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    private GPSTracker tracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        tracker = new GPSTracker(this);


        pubSetup = new PubSetup();
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

        if(tracker.checkLocationPermission() && tracker.getCurrentLocation() != null){
            tracker.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, tracker.locationListener);
            mMap.setMyLocationEnabled(true);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(tracker.getCurrentLocation().getLatitude(),tracker.getCurrentLocation().getLongitude()), 15.0f));
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
                    marker.setTitle(formatMarkerTitle(thisPub.name, HelperMethods.CalculationByDistance(curLatLng, thisPub.coordinates)));
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
                intent.putExtra("name", thisPub.name);
                Bundle args = new Bundle();
                args.putParcelable("coordinates", thisPub.coordinates);

                // Will have to be fixed

                intent.putExtra("workingHoursList", thisPub.weekOpeningHours.openingHours);
                intent.putExtra("workingHours",thisPub.weekOpeningHours.ContentToString());
                intent.putExtra("prices", thisPub.prices.ContentToString());
                intent.putExtra("facilitiesList", thisPub.facilities.facilities);
                intent.putExtra("ratings", thisPub.ratings.ContentToString());
                intent.putExtra("ratingsList", thisPub.ratings.ratings);
                intent.putExtra("averageRating", thisPub.ratings.averageRating);

                intent.putExtra("bundle", args);

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
                tracker.updateCurrentLocation();
                LatLng curLatLng = tracker.getCurrentLocation()  == null ? null : new LatLng(tracker.getCurrentLocation().getLatitude(),tracker.getCurrentLocation().getLongitude());

                String title = formatMarkerTitle(thisPub.name, HelperMethods.CalculationByDistance(curLatLng, thisPub.coordinates));

                thisPub.marker = mMap.addMarker(new MarkerOptions().position(thisPub.coordinates).title(title));

        }

    }

    private String formatMarkerTitle(String name, double distance){

        double rounded = Math.round(distance * 100.0) / 100.0;

        String s = tracker.getCurrentLocation() == null ? name : name + " " + String.valueOf(rounded) + "km";
        return s;
    }


}