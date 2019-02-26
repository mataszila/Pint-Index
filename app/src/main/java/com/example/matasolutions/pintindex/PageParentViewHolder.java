package com.example.matasolutions.pintindex;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class PageParentViewHolder extends GroupViewHolder {

    private TextView mTextView;
    private ImageView mArrow;

    public PageParentViewHolder(View itemView) {
        super(itemView);

        mTextView = itemView.findViewById(R.id.parentTextView);
        mArrow = itemView.findViewById(R.id.arrow);

    }
    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        mArrow.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        mArrow.setAnimation(rotate);
    }


    public void bind(PubPageContentParent parent){
        mTextView.setText(parent.getTitle());
    }

}
