package com.example.matasolutions.pintindex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    DrawerLayout drawer;

    GPSTracker tracker;

    Button login_button;

    FirebaseUser currentUser;

    private com.rengwuxian.materialedittext.MaterialEditText email;
    private com.rengwuxian.materialedittext.MaterialEditText password;

    private FirebaseAuth mAuth;

    private TextView register;

    LinearLayout layout;

    PubSetup setup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        setup = new PubSetup();

        tracker = new GPSTracker(this);

        if(!statusCheck()){

            buildAlertMessageNoGps();
        }

        setTitle("Pint Index");
        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        email = findViewById(R.id.login_email_input);
        password = findViewById(R.id.login_password_input);

        login_button =  findViewById(R.id.button_login);
        register = findViewById(R.id.textView_register);


        if(currentUser == null){

            login_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (view == login_button){
                        LoginUser();
                    }

                }
            });

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(),SignupActivity.class));
                }
            });

        }

        else{

            Toast.makeText(this, "Welcome back " + currentUser.getEmail() , Toast.LENGTH_LONG);
            startActivity(new Intent(getApplicationContext(),WelcomeBackActivity.class));
        }

        layout = findViewById(R.id.start_activity_layout);
        layout.setVisibility(View.VISIBLE);

    }

    public void LoginUser() {
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUser = mAuth.getCurrentUser();
                            finish();
                            startActivity(new Intent(getApplicationContext(),
                                    WelcomeBackActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "couldn't login",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public  boolean statusCheck() {
        final LocationManager manager = tracker.locationManager;

        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            return true;

        }

        return false;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });


        final AlertDialog alert = builder.create();
        alert.show();

    }

}
