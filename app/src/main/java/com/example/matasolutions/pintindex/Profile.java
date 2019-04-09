package com.example.matasolutions.pintindex;

import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class Profile {

    private FirebaseAuth mAuth;
    private FirebaseUser user;


    public String user_email;
    public String user_uID;

    public String gender;
    public String age;


    public ArrayList<String> ratedPubIds;
    public ArrayList<RatingEntry> ratingEntries;


    public Profile(){

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if(user !=null){
            user_email =user.getEmail();
            user_uID = user.getUid();
            gender = "male";
            age = "18";
        }

        ratedPubIds = new ArrayList<>();
        ratingEntries = new ArrayList<>();






        }

    public boolean CheckIfNotRatedYet(String givenID){

        if(ratedPubIds.isEmpty()) {

            return true;

        }

        else{

            for(String id : ratedPubIds){

                if(id.equals(givenID)){

                    return false;
                }
            }
            return true;
        }
    }





}
