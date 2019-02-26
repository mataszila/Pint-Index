package com.example.matasolutions.pintindex;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class PubPageContentParent extends ExpandableGroup<PubPageContentChild> {

    public String name;

    public PubPageContentParent(String title, List<PubPageContentChild> items) {
        super(title, items);
    }
}
