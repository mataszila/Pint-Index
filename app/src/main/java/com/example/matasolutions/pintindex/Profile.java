package com.example.matasolutions.pintindex;

import android.provider.ContactsContract;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.annotation.NonNull;

public class Profile {

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private FirebaseDatabase database;
    private DatabaseReference myRef;


    public String user_email;
    public String user_uID;

    public String gender;
    public String age;

    public ArrayList<RatingEntry> ratingEntries;

    ArrayList<PubRatingEntry> pubRatingEntries;

    public Profile(){

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("userData");

        if(user != null){
            user_email =user.getEmail();
            user_uID = user.getUid();
            gender = "male";
            age = "18";
        }

        ratingEntries = new ArrayList<>();
        pubRatingEntries = new ArrayList<PubRatingEntry>();

        }

     boolean CheckIfNotRatedYet(String givenID){

        if(pubRatingEntries == null) {

            return true;
        }

        if(pubRatingEntries.isEmpty()){
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


     void ReadData(final RateActivityCallback myCallback){

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                pubRatingEntries = ConvertSnapshot(dataSnapshot);

                myCallback.onProfileInfoCallback(pubRatingEntries);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private ArrayList<PubRatingEntry> ConvertSnapshot(DataSnapshot dataSnapshot){

        dataSnapshot = dataSnapshot.child(user_uID).child("pubRatingEntries");

       // GenericTypeIndicator<ArrayList<PubRatingEntry>> t = new GenericTypeIndicator<ArrayList<PubRatingEntry>>() {};

        //ArrayList<PubRatingEntry> list2  = dataSnapshot.getValue(t);

        ArrayList<PubRatingEntry> list = new ArrayList<>();

        for(DataSnapshot snap : dataSnapshot.getChildren()){

            PubRatingEntry entry = snap.getValue(PubRatingEntry.class);
            list.add(entry);

        }



        return list;

    }



}
