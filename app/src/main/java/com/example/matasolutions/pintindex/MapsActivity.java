package com.example.matasolutions.pintindex;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.text.DecimalFormat;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location currentLocation;
    private PubSetup pubSetup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        locationListener = setupLocationListener();
        pubSetup = new PubSetup();


        setupPermissions();
        updateCurrentLocation();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private void updateCurrentLocation() {

        if(checkLocationPermission()){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
            currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        else{
            setupPermissions();
        }

    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setupMap();
    }

    private void setupMap() {

        if(checkLocationPermission()){
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            mMap.setMyLocationEnabled(true);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()), 15.0f));
        }

        setupMarkers();

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if(marker.equals(marker)){
                    updateCurrentLocation();
                    Pub thisPub = pubLookupByMarker(marker);
                    LatLng curLatLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
                    marker.setTitle(formatMarkerTitle(thisPub.name, CalculationByDistance(curLatLng, thisPub.coordinates)));
                }

                return false;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                updateCurrentLocation();
                Pub thisPub = pubLookupByMarker(marker);

                Intent intent = new Intent(getApplicationContext(),PubActivity.class);
                intent.putExtra("name", thisPub.name);
                Bundle args = new Bundle();
                args.putParcelable("coordinates", thisPub.coordinates);

                // Will have to be fixed

                intent.putExtra("workingHours",thisPub.weekOpeningHours.ContentToString());
                intent.putExtra("prices", thisPub.prices.ContentToString());
                intent.putExtra("facilities", thisPub.facilities.ContentToString());
                intent.putExtra("facilitiesList", thisPub.facilities.facilities);
                intent.putExtra("ratings", thisPub.ratings.ContentToString());

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
            updateCurrentLocation();
            LatLng curLatLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());

            String title = formatMarkerTitle(thisPub.name, CalculationByDistance(curLatLng, thisPub.coordinates));


            thisPub.marker = mMap.addMarker(new MarkerOptions().position(thisPub.coordinates).title(title));

        }

    }

    private String formatMarkerTitle(String name, double distance){

        double rounded = Math.round(distance * 100.0) / 100.0;

        String s = name + " " + String.valueOf(rounded) + "km";
        return s;
    }


    private LocationListener setupLocationListener() {
        return new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
                currentLocation = location;
                Log.d("Location: ", location.toString());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
    }

    private void setupPermissions(){

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.

            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);
        }

        else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            }

        }
    }

    public boolean checkLocationPermission()
    {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }




}