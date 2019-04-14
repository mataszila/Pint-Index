package com.example.matasolutions.pintindex;


import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;

class Ratings implements PubPageContentInterface,Parcelable {

    public  PubPageCategory category = PubPageCategory.RATINGS;

    public ArrayList<Rating> ratings;
    public double globalAverageRating;


    public Ratings(ArrayList<Rating> ratings){

        this.ratings = ratings;
        ConvertList();
        this.globalAverageRating = calculateAverageRating();

    }

    public Ratings(){

    }


    public void AddNewEntry(RatingEntry entry){

        for(int i=0;i<ratings.size();i++){

            Rating thisRating = ratings.get(i);

            if(entry.ratingType == thisRating.type){

                thisRating.ratingEntries.add(entry);

                thisRating.setAverageRating();

                globalAverageRating = calculateAverageRating();


            }

        }
    }




    @Override
    public String ContentToString() {

        if(ratings != null){

            StringBuilder sb = new StringBuilder();

            for(int i=0;i<ratings.size();i++){

                Rating current = ratings.get(i);

                sb.append(current.type.toString() + " " + current.averageRating + "\n");

            }

            return sb.toString();

        }

        else{
            return "Ratings not available";

        }

    }

    private void ConvertList() {

        ArrayList<Rating> compare = new ArrayList<>();

        if (ratings != null) {

            for (int i = ratings.size()-1; i >= 0 ; i--) {

                Rating thisRating = ratings.get(i);

                if(i == ratings.size()-1){

                    compare.add(thisRating);
                    continue;
                }
                int counter=  0;

                for(int j = 0 ; j< compare.size();j++){

                    Rating compRating = compare.get(j);

                    if(thisRating.type != compRating.type){
                        counter++;
                    }
                    if(counter == compare.size()){
                        compare.add(thisRating);
                    }
                }
            }
            ratings = compare;
        }
    }


    private double calculateAverageRating(){

        if(ratings == null) {

            return 0;
        }

        double sum = 0;

        for(int i=0;i<ratings.size();i++){

            sum+=ratings.get(i).averageRating;

        }

        return sum/ratings.size();
    }

    protected Ratings(Parcel in) {
        category = (PubPageCategory) in.readValue(PubPageCategory.class.getClassLoader());
        if (in.readByte() == 0x01) {
            ratings = new ArrayList<Rating>();
            in.readList(ratings, Rating.class.getClassLoader());
        } else {
            ratings = null;
        }
        globalAverageRating = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(category);
        if (ratings == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ratings);
        }
        dest.writeDouble(globalAverageRating);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ratings> CREATOR = new Parcelable.Creator<Ratings>() {
        @Override
        public Ratings createFromParcel(Parcel in) {
            return new Ratings(in);
        }

        @Override
        public Ratings[] newArray(int size) {
            return new Ratings[size];
        }
    };
}




