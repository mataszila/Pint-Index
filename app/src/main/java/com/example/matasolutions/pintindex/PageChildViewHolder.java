package com.example.matasolutions.pintindex;

import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class PubPageContentChildViewHolder extends ChildViewHolder {

    private TextView mTextView;


    public PubPageContentChildViewHolder(View itemView) {

        super(itemView);
        mTextView = itemView.findViewById(R.id.childTextView);
    }

    public void bind(PubPageContentChild child){
        mTextView.setText(child.name);
    }

}
