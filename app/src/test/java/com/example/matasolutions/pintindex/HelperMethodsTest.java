package com.example.matasolutions.pintindex;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.junit.Test;

import androidx.constraintlayout.solver.widgets.Helper;

import static org.junit.Assert.*;

public class HelperMethodsTest {


    @Test
    public void getHoursMinutesNow() {
    }

    @Test
    public void calculationByDistance() {


        //Newcastle
        LatLng ncl_Lat = new LatLng(54.9783, 1.6178);
        //London
        LatLng ldn_lat = new LatLng(51.5074, 0.1278);
        //Amsterdam
        LatLng ams_lat = new LatLng(52.3680, 4.9036);

        double actualResult1 = HelperMethods.CalculationByDistance(ncl_Lat,ldn_lat);

        double expectedResult1 = 398.45;

        assertEquals(expectedResult1, actualResult1,1);

        double actualResult2 = HelperMethods.CalculationByDistance(ncl_Lat,ams_lat);

        double expectedResult2 = 361.98;

        assertEquals(expectedResult2, actualResult2,1);



    }


    @Test
    public void isPubOpen() {
    }

    @Test
    public void lookupProductPrice() {
    }

    @Test
    public void doProductsMatch() {
    }

    @Test
    public void findProductinPub() {
    }

    @Test
    public void findPubsWithProduct() {
    }
}