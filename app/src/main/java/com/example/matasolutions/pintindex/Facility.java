package com.example.matasolutions.pintindex;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.ImageView;

import java.io.Serializable;

class Facility implements Serializable {

    public String name;
    public FacilityType type;
    public Drawable logo;
    final PubPageCategory category = PubPageCategory.FACILITIES;


    public Facility(FacilityType type,String name){
        this.type = type;
        this.name = name;
    }
}
