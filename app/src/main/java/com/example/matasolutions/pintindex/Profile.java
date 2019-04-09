package com.example.matasolutions.pintindex;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Profile {

    private FirebaseAuth mAuth;
    private FirebaseUser user;


    private String user_email;
    private String user_uID;

    public ArrayList<String> ratedPubIds;
    public ArrayList<RatingEntry> ratingEntries;


    public Profile(){

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if(user !=null){
            user_email =user.getEmail();
            user_uID = user.getUid();
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
