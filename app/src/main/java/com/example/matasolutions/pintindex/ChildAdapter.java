package com.example.matasolutions.pintindex;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class ChildAdapter extends ExpandableRecyclerViewAdapter<PageParentViewHolder,PageChildViewHolder> {

    public ChildAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public PageParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_recycler_view_parent,parent,false);
        return new PageParentViewHolder(v);
    }

    @Override
    public PageChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_recycler_view_child,parent,false);
        return new PageChildViewHolder(v);
    }

    @Override
    public void onBindChildViewHolder(PageChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {

        final PubPageContentChild pubPageContentChild = (PubPageContentChild) group.getItems().get(childIndex);
        holder.bind(pubPageContentChild);

    }

    @Override
    public void onBindGroupViewHolder(PageParentViewHolder holder, int flatPosition, ExpandableGroup group) {

        final PubPageContentParent pubPageContentParent = (PubPageContentParent) group;
        holder.bind(pubPageContentParent);

    }
}
