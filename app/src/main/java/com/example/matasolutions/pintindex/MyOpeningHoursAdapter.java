package com.example.matasolutions.pintindex;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.constraintlayout.solver.widgets.Helper;
import androidx.recyclerview.widget.RecyclerView;


public class MyOpeningHoursAdapter extends RecyclerView.Adapter<MyOpeningHoursAdapter.MyOpeningHoursViewHolder> {
    private ArrayList<SingleOpeningHours> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyOpeningHoursViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView dayOfTheWeek;
        public TextView openingHours;


        public MyOpeningHoursViewHolder(View v) {
            super(v);

            dayOfTheWeek =  v.findViewById(R.id.pub_activity_openinghours_dayoftheweek);
            openingHours =  v.findViewById(R.id.pub_activity_openinghours_times);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyOpeningHoursAdapter(ArrayList<SingleOpeningHours> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyOpeningHoursAdapter.MyOpeningHoursViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.pub_activity_openinghours_recycler_view, parent, false);

        MyOpeningHoursAdapter.MyOpeningHoursViewHolder vh = new MyOpeningHoursAdapter.MyOpeningHoursViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyOpeningHoursAdapter.MyOpeningHoursViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        SingleOpeningHours thisOpeningHours = mDataset.get(position);

        int currentDayOfTheWeek = HelperMethods.GetCorrectDayOfWeek();

        // Set item views based on your views and data model
        TextView dayOfTheWeek = holder.dayOfTheWeek;
        TextView openingHours  = holder.openingHours;

        dayOfTheWeek.setText(thisOpeningHours.dayOfTheWeek.toString());
        openingHours.setText(thisOpeningHours.openingTime + "-" + thisOpeningHours.closingTime);

        if(position+1 == currentDayOfTheWeek){

            dayOfTheWeek.setTypeface(null, Typeface.BOLD);
            openingHours.setTypeface(null, Typeface.BOLD);

        }



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}