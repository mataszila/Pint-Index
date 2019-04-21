package com.example.matasolutions.pintindex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText password;
    private EditText confirm_password;


    private EditText email;
    private Button button_register;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = (EditText) findViewById(R.id.signup_email_input);
        password = findViewById(R.id.signup_password_input);
        confirm_password = findViewById(R.id.signup_password_confirm_input);
        button_register = (Button)findViewById(R.id.button_register);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(view == button_register){


                    if(CheckIfPasswordsMatch()){
                        RegisterUser();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Passwords don't match. Please try again.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }


    private boolean CheckIfPasswordsMatch(){

        String comp_password = password.getText().toString().trim();
        String comp_confirm = confirm_password.getText().toString().trim();

        return comp_password.equals(comp_confirm) ? true : false;

    }


    public void RegisterUser(){
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();
        if (TextUtils.isEmpty(Email)){
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Password)){
            Toast.makeText(this, "A Field is Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            //check if successful
                            if (task.isSuccessful()) {
                                //User is successfully registered and logged in
                                //start Profile Activity here
                                Toast.makeText(SignupActivity.this, "Registration successful",
                                        Toast.LENGTH_LONG).show();

                                database = FirebaseDatabase.getInstance();
                                myRef = database.getReference("userData");

                                myRef.child(mAuth.getUid()).setValue(new Profile());


                                finish();
                                startActivity(new Intent(getApplicationContext(), WelcomeBackActivity.class));
                            }else{
                                Toast.makeText(SignupActivity.this, "Couldn't register, try again",
                                        Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
    }


}
