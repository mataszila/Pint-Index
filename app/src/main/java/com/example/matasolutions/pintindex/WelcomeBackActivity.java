package com.example.matasolutions.pintindex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class WelcomeBackActivity extends AppCompatActivity {


    TextView welcome_back_user;
    Button proceed;
    Button logout;

    FirebaseUser currentUser;

    FirebaseAuth mAuth;

    PubSetup pubSetup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_back);

        pubSetup = new PubSetup();

        pubSetup.ReadData(new MyCallback() {
            @Override
            public void onPubCallback(ArrayList<Pub> value) {

                Log.i("CALLBACK_WELCOMEBACK", "LIST SIZE " + value.size());

                pubSetup.pubs = value;

                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);

                intent.putExtra("pubSetup", pubSetup);

                startActivity(intent);

            }
        });

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        welcome_back_user = findViewById(R.id.welcome_back_user_textView);

        welcome_back_user.setText("Welcome, " + currentUser.getEmail());


        logout = findViewById(R.id.button_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),StartActivity.class));
            }
        });

        proceed = findViewById(R.id.button_proceed);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);

                intent.putExtra("pubSetup", pubSetup);

                startActivity(intent);

            }
        });

    }
}
