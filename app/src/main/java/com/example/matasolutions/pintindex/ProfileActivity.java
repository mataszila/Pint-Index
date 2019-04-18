package com.example.matasolutions.pintindex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProfileActivity extends MapsActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;


    private TextView Email;
    private TextView Uid;
    private Button logout;
    private Button button_start;

    private Button button_refresh;

    private FirebaseDatabase database;
    private DatabaseReference myRef;


    private Profile profile;

    private TextView ratedPubs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile = new Profile();

        Email = (TextView) findViewById(R.id.profileEmail);
        Uid = (TextView) findViewById(R.id.profileUid);
        mAuth = FirebaseAuth.getInstance();
        logout = (Button) findViewById(R.id.button_logout);
        button_refresh = findViewById(R.id.button_refresh);

        ratedPubs = findViewById(R.id.ratedPubs);

        button_start = findViewById(R.id.button_start);

        user = mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("userData");
        String id = profile.user_uID;

        SetupButtonListeners();

        if (user != null){
            String email = user.getEmail();
            String uid = user.getUid();
            Email.setText(email);
            Uid.setText(uid);

        }

    }


    private void SetupButtonListeners(){

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (user!=null){

                    mAuth.signOut();

                    startActivity(new Intent(getApplicationContext(),
                            AuthenticationActivity.class));

                }
            }
        });

        button_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            }
        });

        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),MapsActivity.class));

            }
        });




    }



}
