package com.example.matasolutions.pintindex;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class PageChildViewHolder extends ChildViewHolder {

    private TextView mTextView;
    private ImageView mImageView;


    public PageChildViewHolder(View itemView) {

        super(itemView);
        mTextView = itemView.findViewById(R.id.childTextView);
    }

    public void bind(final PubPageContentChild child){
        mTextView.setText(child.name);
        mImageView = itemView.findViewById(R.id.facilityLogo);


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
