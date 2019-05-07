package com.example.matasolutions.pintindex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class MyRatingsAdapter extends RecyclerView.Adapter<MyRatingsAdapter.MyRatingsViewHolder> {
    private ArrayList<Rating> mDataset;
    private String pubID;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyRatingsViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView category;
        public TextView rating;


        public MyRatingsViewHolder(View v) {
            super(v);

            category =  v.findViewById(R.id.pub_activity_ratings_category);
            rating =  v.findViewById(R.id.pub_activity_ratings_rating);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyRatingsAdapter(ArrayList<Rating> myDataset, String pubID) {
        mDataset = myDataset;
        this.pubID = pubID;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyRatingsAdapter.MyRatingsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.pub_activity_ratings_recycler_view, parent, false);

        MyRatingsAdapter.MyRatingsViewHolder vh = new MyRatingsAdapter.MyRatingsViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyRatingsAdapter.MyRatingsViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Rating thisRating = mDataset.get(position);


        // Set item views based on your views and data model
        TextView category = holder.category;
        final TextView rating  = holder.rating;

        category.setText(String.valueOf(thisRating.type));
        rating.setText(String.valueOf(thisRating.averageRating));


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}