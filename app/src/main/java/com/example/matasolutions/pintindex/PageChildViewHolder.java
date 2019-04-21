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

    private RecyclerView prices_recyclerView;
    private RecyclerView.Adapter prices_mAdapter;
    private RecyclerView.LayoutManager prices_layoutManager;

    private LinearLayout facilitiesLinearLayout;

    private RecyclerView facilities_recyclerView;
    private RecyclerView.Adapter facilities_mAdapter;
    private RecyclerView.LayoutManager facilities_layoutManager;


    public PageChildViewHolder(View itemView) {

        super(itemView);
        mTextView = itemView.findViewById(R.id.childTextView);
    }

    public void bind(final PubPageContentChild child) {
        mTextView.setText(child.name);
        mImageView = itemView.findViewById(R.id.facilityLogo);


        if (child.type == PubPageCategory.PRICES) {

            pricesLinearLayout = itemView.findViewById(R.id.child_prices);
            pricesLinearLayout.setVisibility(View.VISIBLE);

            prices_recyclerView = itemView.findViewById(R.id.child_prices_recyclerView);

            prices_recyclerView.setHasFixedSize(true);

            prices_layoutManager = new LinearLayoutManager(itemView.getContext());
            prices_recyclerView.setLayoutManager(prices_layoutManager);

            prices_mAdapter = new MyPricesAdapter(child.pub.prices.priceList);
            prices_recyclerView.setAdapter(prices_mAdapter);

        } else if (child.type == PubPageCategory.FACILITIES) {

            facilitiesLinearLayout = itemView.findViewById(R.id.child_facilities);
            facilitiesLinearLayout.setVisibility(View.VISIBLE);

            facilities_recyclerView = itemView.findViewById(R.id.child_facilities_recyclerView);

            facilities_recyclerView.setHasFixedSize(true);

            facilities_layoutManager = new LinearLayoutManager(itemView.getContext());
            facilities_recyclerView.setLayoutManager(facilities_layoutManager);

            facilities_mAdapter = new MyFacilitiesAdapter(child.pub.facilities.facilities);
            facilities_recyclerView.setAdapter(facilities_mAdapter);

        }

    }






}
