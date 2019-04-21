package com.example.matasolutions.pintindex;

import androidx.annotation.NonNull;
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
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class PubActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Pub pub;

    private String pubID;

    private LatLng coordinates;

    TextView toolbar_text_1;
    TextView toolbar_text_2;
    TextView toolbar_text_4;

    CardView toolbar_card_1;
    CardView rate_card;
    CardView compare_with_card;

    ArrayList<ImageView> facilityLogos;

    RecyclerView toolbar_recyclerview;
    LinearLayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference myRef;

    LinearLayout mainLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("pubsList");

        pubID = getIntent().getExtras().getString("pubID");

        ReadSinglePub(new PubActivityCallback() {
            @Override
            public void onSinglePubCallBack(Pub value) {

                mainLayout = findViewById(R.id.pub_activity_layout);
                mainLayout.setVisibility(View.VISIBLE);

                findViewById(R.id.loadingPanel_pub).setVisibility(View.GONE);

                setupPub(value);
                SetupToolbar();
                AddPubPageContent();

            }
        },pubID);


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
    }

    private void setupPub(Pub db_pub){

        pub = db_pub;

        LatLng sydney = pub.getCoordinates();

        mMap.addMarker(new MarkerOptions().position(sydney).title(pub.name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(pub.coordinates.getLatitude(),pub.coordinates.getLongitude()), 15.0f));


        setTitle(pub.name);
    }


    private void SetupToolbar(){

        toolbar_text_1 = findViewById(R.id.toolbar_text_1);
        toolbar_text_1.setText(HelperMethods.SetPubOpeningStatus(pub));

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
                intent.putExtra("name", pub.name);
                intent.putExtra("pub", pub);
                Bundle args = new Bundle();
                args.putParcelable("coordinates", HelperMethods.convertLatLng(pub.coordinates));
                intent.putExtra("bundle", args);

                finish();
                startActivity(intent);
            }
        });


        toolbar_text_4.setText("COMPARE WITH...");

        compare_with_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    new PubSetup().ReadData(new MyCallback() {
                        @Override
                        public void onPubCallback(ArrayList<Pub> value) {

                            PubSetup pubSetup = new PubSetup();
                            pubSetup.pubs = value;

                            Intent intent = new Intent(getApplicationContext(),PubCompareActivity.class);
                            intent.putExtra("pubName",pub.name);
                            intent.putExtra("pubSetup", pubSetup);
                            startActivity(intent);

                        }
                    });

            }
        });

    }


    private String ShowRatingText(){

        NumberFormat formatter = new DecimalFormat("#0.00");

        return formatter.format(pub.ratings.globalAverageRating) + "/5";
    }


    private void ReadSinglePub(final PubActivityCallback myCallback, final String pubID){

            myRef.child("list").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Pub pub =  dataSnapshot.child(pubID).getValue(Pub.class);

                    myCallback.onSinglePubCallBack(pub);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
    }

    private void AddPubPageContent() {

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        ArrayList<PubPageContentParent> parentList = new ArrayList<PubPageContentParent>();

        //WeekOpeningHours

        ArrayList<PubPageContentChild> openingHoursChild = new ArrayList<PubPageContentChild>();

        //    public PubPageContentChild(Pub pub ,PubPageCategory type) {

        openingHoursChild.add(new PubPageContentChild(pub,PubPageCategory.OPENING_HOURS));

        parentList.add(new PubPageContentParent("Opening Hours", openingHoursChild));

        //Prices

        ArrayList<PubPageContentChild> pricesChild = new ArrayList<PubPageContentChild>();

        pricesChild.add(new PubPageContentChild(pub,PubPageCategory.PRICES));

        parentList.add(new PubPageContentParent("Prices", pricesChild));

        //Facilities

        ArrayList<PubPageContentChild> facilitiesChild = new ArrayList<PubPageContentChild>();

        facilitiesChild.add(new PubPageContentChild(pub,PubPageCategory.FACILITIES));

        parentList.add(new PubPageContentParent("Facilities", facilitiesChild));

        //Rating

        ArrayList<PubPageContentChild> ratingsChild = new ArrayList<PubPageContentChild>();

        ratingsChild.add(new PubPageContentChild(pub,PubPageCategory.RATINGS));

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

