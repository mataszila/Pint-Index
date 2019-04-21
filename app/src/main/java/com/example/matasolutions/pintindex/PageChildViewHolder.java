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



    public PageChildViewHolder(View itemView) {

        super(itemView);
        mTextView = itemView.findViewById(R.id.childTextView);
    }

    public void bind(final PubPageContentChild child){
        mTextView.setText(child.name);
        mImageView = itemView.findViewById(R.id.facilityLogo);


        if(child.type == PubPageCategory.PRICES){

            pricesLinearLayout = itemView.findViewById(R.id.child_prices);
            pricesLinearLayout.setVisibility(View.VISIBLE);

            prices_recyclerView =  itemView.findViewById(R.id.child_prices_recyclerView);

            prices_recyclerView.setHasFixedSize(true);

            prices_layoutManager = new LinearLayoutManager(itemView.getContext());
            prices_recyclerView.setLayoutManager(prices_layoutManager);

            prices_mAdapter = new MyPricesAdapter(child.pub.prices.priceList);
            prices_recyclerView.setAdapter(prices_mAdapter);

        }




        if(child.type == PubPageCategory.FACILITIES){

            mImageView.setImageResource(PubSetup.ReturnResourceID(child.facility));
            mImageView.setVisibility(View.VISIBLE);
            mTextView.setVisibility(View.GONE);

            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mTextView.setVisibility(View.VISIBLE);
                }
            });


        }
        else{
            mImageView.setVisibility(View.GONE);
        }

    }






}
