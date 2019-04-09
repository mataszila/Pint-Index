package com.example.matasolutions.pintindex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;



    private TextView Email;
    private TextView Uid;
    private Button logout;
    private Button button_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Email = (TextView) findViewById(R.id.profileEmail);
        Uid = (TextView) findViewById(R.id.profileUid);
        mAuth = FirebaseAuth.getInstance();
        logout = (Button) findViewById(R.id.button_logout);


        button_start = findViewById(R.id.button_start);

        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),MapsActivity.class));

            }
        });

        user = mAuth.getCurrentUser();

        if (user != null){
            String email = user.getEmail();
            String uid = user.getUid();
            Email.setText(email);
            Uid.setText(uid);
        }

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

    }




}
