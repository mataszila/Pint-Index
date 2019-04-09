package com.example.matasolutions.pintindex;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Profile {

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    public ArrayList<String> ratedPubIds;


    public Profile(){

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        ratedPubIds = new ArrayList<>();


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
