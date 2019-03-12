package com.example.matasolutions.pintindex;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class PageChildViewHolder extends ChildViewHolder {

    private TextView mTextView;
    private ImageView mImageView;


    public PageChildViewHolder(View itemView) {

        super(itemView);
        mTextView = itemView.findViewById(R.id.childTextView);
        mImageView = itemView.findViewById(R.id.facilityLogo);
    }

    public void bind(PubPageContentChild child){
        mTextView.setText(child.name);
        mImageView.setImageResource(PubSetup.ReturnResourceID(child.facility));

    }



}
