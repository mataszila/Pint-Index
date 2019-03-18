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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

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

        toolbar_text_1.setText(SetPubOpeningStatus());

    }

    

    private String SetPubOpeningStatus(){

        SingleOpeningHours hoursForToday  = pub.weekOpeningHours.openingHours.get(GetCorrectDayOfWeek()-1);


        try {
            if(isPubOpen(hoursForToday.openingTime,hoursForToday.closingTime ,getHoursMinutesNow() )){


                return FormatStatusText("OPEN", "CLOSES", hoursForToday.closingTime);
            }
            else{
                return FormatStatusText("CLOSED", "OPENS", hoursForToday.openingTime);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "STATUS N/A";

    }

    private String FormatStatusText(String status, String action, String actionTime) {

        String ans = status + " (" + action + actionTime + ")";

        return ans;



    }


    private int GetCorrectDayOfWeek(){

        Calendar calendar  = Calendar.getInstance();
        int incorrect = calendar.get(Calendar.DAY_OF_WEEK);

        int ans  = incorrect == 1 ? 7 : incorrect - 1;
        return ans;

    }


    private String getHoursMinutesNow(){

        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance

        int numHours = calendar.get(Calendar.HOUR_OF_DAY);
        int numMinutes = calendar.get(Calendar.MINUTE);

        String hours = String.valueOf(numHours);
        String minutes = numMinutes < 10 ? "0" + String.valueOf(numMinutes) : String.valueOf(numMinutes);  // gets hour in 24h format

        return hours + ":" + minutes;

    }


    public static boolean isPubOpen(String argStartTime,
                                               String argEndTime, String argCurrentTime) throws ParseException, ParseException {
        String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9])$";
        //
        if (argStartTime.matches(reg) && argEndTime.matches(reg)
                && argCurrentTime.matches(reg)) {
            boolean valid = false;
            // Start Time
            java.util.Date startTime = new SimpleDateFormat("HH:mm")
                    .parse(argStartTime);
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startTime);

            // Current Time
            java.util.Date currentTime = new SimpleDateFormat("HH:mm")
                    .parse(argCurrentTime);
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(currentTime);

            // End Time
            java.util.Date endTime = new SimpleDateFormat("HH:mm")
                    .parse(argEndTime);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endTime);

            //
            if (currentTime.compareTo(endTime) < 0) {

                currentCalendar.add(Calendar.DATE, 1);
                currentTime = currentCalendar.getTime();

            }

            if (startTime.compareTo(endTime) < 0) {

                startCalendar.add(Calendar.DATE, 1);
                startTime = startCalendar.getTime();

            }
            //
            if (currentTime.before(startTime)) {

                System.out.println(" Time is Lesser ");

                valid = false;
            } else {

                if (currentTime.after(endTime)) {
                    endCalendar.add(Calendar.DATE, 1);
                    endTime = endCalendar.getTime();

                }

                System.out.println("Comparing , Start Time /n " + startTime);
                System.out.println("Comparing , End Time /n " + endTime);
                System.out
                        .println("Comparing , Current Time /n " + currentTime);

                if (currentTime.before(endTime)) {
                    System.out.println("RESULT, Time lies b/w");
                    valid = true;
                } else {
                    valid = false;
                    System.out.println("RESULT, Time does not lies b/w");
                }

            }
            return valid;

        } else {
            throw new IllegalArgumentException(
                    "Not a valid time, expecting HH:MM:SS format");
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

