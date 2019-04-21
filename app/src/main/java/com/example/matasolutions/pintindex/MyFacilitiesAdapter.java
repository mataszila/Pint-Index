package com.example.matasolutions.pintindex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;


public class MyFacilitiesAdapter extends RecyclerView.Adapter<MyFacilitiesAdapter.MyFacilitiesViewHolder> {
    private ArrayList<Facility> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyFacilitiesViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public ImageView image;
        public TextView text;


        public MyFacilitiesViewHolder(View v) {
            super(v);

            image =  v.findViewById(R.id.pub_activity_facilities_image);
            text =  v.findViewById(R.id.pub_activity_facilities_text);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyFacilitiesAdapter(ArrayList<Facility> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyFacilitiesAdapter.MyFacilitiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.pub_activity_facilities_recycler_view, parent, false);

        MyFacilitiesAdapter.MyFacilitiesViewHolder vh = new MyFacilitiesAdapter.MyFacilitiesViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyFacilitiesAdapter.MyFacilitiesViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Facility thisFacility = mDataset.get(position);

        // Set item views based on your views and data model
        ImageView image = holder.image;
        TextView text  = holder.text;

        image.setImageResource(PubSetup.ReturnResourceID(thisFacility));
        text.setText(thisFacility.name);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}