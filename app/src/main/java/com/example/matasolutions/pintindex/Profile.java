package com.example.matasolutions.pintindex;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Profile {

    private FirebaseAuth mAuth;
    private FirebaseUser user;


    public String user_email;
    public String user_uID;

    public String gender;
    public String age;

    public ArrayList<RatingEntry> ratingEntries;

    ArrayList<PubRatingEntry> pubRatingEntries;

    public Profile(){

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if(user !=null){
            user_email =user.getEmail();
            user_uID = user.getUid();
            gender = "male";
            age = "18";
        }

        ratingEntries = new ArrayList<>();
        pubRatingEntries = new ArrayList<PubRatingEntry>();

        }

    public boolean CheckIfNotRatedYet(String givenID){

        if(pubRatingEntries.isEmpty()) {

            return true;

        }

        else{

            for(PubRatingEntry entry : pubRatingEntries){

                if(entry.pubID.equals(givenID)){

                    return false;

                }
            }
            return true;
        }
    }





}
