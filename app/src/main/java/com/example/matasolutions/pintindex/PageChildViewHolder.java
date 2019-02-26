package com.example.matasolutions.pintindex;

import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

public class PageChildViewHolder extends ChildViewHolder {

    private TextView mTextView;


    public PageChildViewHolder(View itemView) {

        super(itemView);
        mTextView = itemView.findViewById(R.id.childTextView);
    }

    public void bind(PubPageContentChild child){
        mTextView.setText(child.name);
    }

}
