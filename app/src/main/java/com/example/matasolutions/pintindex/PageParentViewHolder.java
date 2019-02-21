package com.example.matasolutions.pintindex;

import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

public class PubPageContentParentViewHolder extends GroupViewHolder {

    private TextView mTextView;

    public PubPageContentParentViewHolder(View itemView) {
        super(itemView);

        mTextView = itemView.findViewById(R.id.parentTextView);



    }

    public void bind(PubPageContentParent parent){
        mTextView.setText(parent.getTitle());
    }

}
