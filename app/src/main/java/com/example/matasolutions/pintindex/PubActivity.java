package com.example.matasolutions.pintindex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.awt.font.TextAttribute;
import java.io.Serializable;

public class PubActivity extends AppCompatActivity implements Serializable {

    Pub pub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub);

        setupPub();

        TextView pubLocation = findViewById(R.id.pubLocation);
        pubLocation.setText(getCoordinatesAsText());

    }

    private void setupPub(){

        pub = new Pub();

        Bundle bundle = getIntent().getParcelableExtra("bundle");
        pub.coordinates = bundle.getParcelable("coordinates");

        pub.name = (String) getIntent().getSerializableExtra("name");

        setTitle(pub.name);

    }

    private String getCoordinatesAsText(){
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(pub.coordinates.latitude));
        sb.append(" ");
        sb.append(String.valueOf(pub.coordinates.longitude));

        return sb.toString();

    }



}
