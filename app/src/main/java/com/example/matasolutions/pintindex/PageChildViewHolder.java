package com.example.matasolutions.pintindex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PageChildViewHolder extends ChildViewHolder {

    private TextView mTextView;
    private ImageView mImageView;

    private LinearLayout pricesLinearLayout;
    private LinearLayout facilitiesLinearLayout;
    private LinearLayout ratingsLinearLayout;
    private LinearLayout openinghoursLinearLayout;



    private RecyclerView prices_recyclerView;
    private RecyclerView.Adapter prices_mAdapter;
    private RecyclerView.LayoutManager prices_layoutManager;


    private RecyclerView facilities_recyclerView;
    private RecyclerView.Adapter facilities_mAdapter;
    private RecyclerView.LayoutManager facilities_layoutManager;


    private RecyclerView ratings_recyclerView;
    private RecyclerView.Adapter ratings_mAdapter;
    private RecyclerView.LayoutManager ratings_layoutManager;


    private RecyclerView openinghours_recyclerView;
    private RecyclerView.Adapter openinghours_mAdapter;
    private RecyclerView.LayoutManager openinghours_layoutManager;




    public PageChildViewHolder(View itemView) {

        super(itemView);
        mTextView = itemView.findViewById(R.id.childTextView);
    }

    public void bind(final PubPageContentChild child) {
        mTextView.setText(child.name);
        mImageView = itemView.findViewById(R.id.facilityLogo);

        pricesLinearLayout = itemView.findViewById(R.id.child_prices);
        facilitiesLinearLayout = itemView.findViewById(R.id.child_facilities);
        ratingsLinearLayout = itemView.findViewById(R.id.child_ratings);
        openinghoursLinearLayout = itemView.findViewById(R.id.child_openinghours);

        switch (child.type){

            case PRICES:

                facilitiesLinearLayout.setVisibility(View.GONE);
                ratingsLinearLayout.setVisibility(View.GONE);
                openinghoursLinearLayout.setVisibility(View.GONE);


                pricesLinearLayout.setVisibility(View.VISIBLE);

                prices_recyclerView = itemView.findViewById(R.id.child_prices_recyclerView);

                prices_recyclerView.setHasFixedSize(true);

                prices_layoutManager = new LinearLayoutManager(itemView.getContext());
                prices_recyclerView.setLayoutManager(prices_layoutManager);

                prices_mAdapter = new MyPricesAdapter(child.pub.prices.priceList);
                prices_recyclerView.setAdapter(prices_mAdapter);


                break;
            case FACILITIES:

                pricesLinearLayout.setVisibility(View.GONE);
                ratingsLinearLayout.setVisibility(View.GONE);
                openinghoursLinearLayout.setVisibility(View.GONE);


                facilitiesLinearLayout.setVisibility(View.VISIBLE);

                facilities_recyclerView = itemView.findViewById(R.id.child_facilities_recyclerView);

                facilities_recyclerView.setHasFixedSize(true);

                facilities_layoutManager = new LinearLayoutManager(itemView.getContext());
                facilities_recyclerView.setLayoutManager(facilities_layoutManager);

                facilities_mAdapter = new MyFacilitiesAdapter(child.pub.facilities.facilities);
                facilities_recyclerView.setAdapter(facilities_mAdapter);


                break;
            case RATINGS:

                pricesLinearLayout.setVisibility(View.GONE);
                facilitiesLinearLayout.setVisibility(View.GONE);
                openinghoursLinearLayout.setVisibility(View.GONE);

                ratingsLinearLayout.setVisibility(View.VISIBLE);

                ratings_recyclerView = itemView.findViewById(R.id.child_ratings_recyclerView);

                ratings_recyclerView.setHasFixedSize(true);

                ratings_layoutManager = new LinearLayoutManager(itemView.getContext());
                ratings_recyclerView.setLayoutManager(ratings_layoutManager);

                ratings_mAdapter = new MyRatingsAdapter(child.pub.ratings.ratings);
                ratings_recyclerView.setAdapter(ratings_mAdapter);
                break;
            case OPENING_HOURS:

                pricesLinearLayout.setVisibility(View.GONE);
                facilitiesLinearLayout.setVisibility(View.GONE);
                ratingsLinearLayout.setVisibility(View.GONE);

                openinghoursLinearLayout.setVisibility(View.VISIBLE);

                openinghours_recyclerView = itemView.findViewById(R.id.child_openinghours_recyclerView);

                openinghours_recyclerView.setHasFixedSize(true);

                openinghours_layoutManager = new LinearLayoutManager(itemView.getContext());
                openinghours_recyclerView.setLayoutManager(openinghours_layoutManager);

                openinghours_mAdapter = new MyOpeningHoursAdapter(child.pub.weekOpeningHours.openingHours);
                openinghours_recyclerView.setAdapter(openinghours_mAdapter);

                break;
        }


    }






}
