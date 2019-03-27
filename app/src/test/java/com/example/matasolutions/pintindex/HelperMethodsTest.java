package com.example.matasolutions.pintindex;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.junit.Test;

import java.text.ParseException;

import androidx.constraintlayout.solver.widgets.Helper;

import static org.junit.Assert.*;

public class HelperMethodsTest {


    @Test
    public void getHoursMinutesNow() {
    }

    @Test
    public void Test_HelperMethods_CalculationByDistance() {

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
    public void Test_HelperMethods_IsPubOpen() {

        try {
            assertTrue(HelperMethods.isPubOpen("15:00","02:00","01:59"));
            assertFalse(HelperMethods.isPubOpen("10:00","02:00","02:30"));
            assertTrue(HelperMethods.isPubOpen("12:00", "01:00", "17:45"));

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void lookupProductPrice() {
    }

    @Test
    public void doProductsMatch() {

        Product p1 = new Product(Brand.HEINEKEN, DrinkType.BEER,Amount.PINT );
        Product p2 = new Product(Brand.HEINEKEN, DrinkType.BEER,Amount.PINT );
        Product p3 = new Product(Brand.HEINEKEN, DrinkType.BEER,Amount.HALF_PINT );
        Product p4 = new Product(Brand.STELLA_ARTOIS, DrinkType.BEER,Amount.PINT );

        assertTrue(HelperMethods.DoProductsMatch(p1, p2));

        assertFalse(HelperMethods.DoProductsMatch(p1, p3));

        assertFalse(HelperMethods.DoProductsMatch(p1,p4 ));

    }

    @Test
    public void findProductinPub() {
    }

    @Test
    public void findPubsWithProduct() {
    }
}