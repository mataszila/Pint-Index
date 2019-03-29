package com.example.matasolutions.pintindex;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class HelperMethods {

    public static int GetCorrectDayOfWeek(){

        Calendar calendar  = Calendar.getInstance();
        int incorrect = calendar.get(Calendar.DAY_OF_WEEK);

        int ans  = incorrect == 1 ? 7 : incorrect - 1;
        return ans;

    }

    public static String getHoursMinutesNow(){

        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance

        int numHours = calendar.get(Calendar.HOUR_OF_DAY);
        int numMinutes = calendar.get(Calendar.MINUTE);

        String hours = String.valueOf(numHours);
        String minutes = numMinutes < 10 ? "0" + String.valueOf(numMinutes) : String.valueOf(numMinutes);  // gets hour in 24h format

        return hours + ":" + minutes;

    }


     static double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));

        return Radius*c;
    }

    public static boolean isPubOpen(String argStartTime,
                                    String argEndTime, String argCurrentTime) throws ParseException, ParseException {
        String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9])$";
        //
        if (argStartTime.matches(reg) && argEndTime.matches(reg)
                && argCurrentTime.matches(reg)) {
            boolean valid = false;
            // Start Time
            java.util.Date startTime = new SimpleDateFormat("HH:mm")
                    .parse(argStartTime);
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.setTime(startTime);

            // Current Time
            java.util.Date currentTime = new SimpleDateFormat("HH:mm")
                    .parse(argCurrentTime);
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(currentTime);

            // End Time
            java.util.Date endTime = new SimpleDateFormat("HH:mm")
                    .parse(argEndTime);
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTime(endTime);

            //
            if (currentTime.compareTo(endTime) < 0) {

                currentCalendar.add(Calendar.DATE, 1);
                currentTime = currentCalendar.getTime();

            }

            if (startTime.compareTo(endTime) < 0) {

                startCalendar.add(Calendar.DATE, 1);
                startTime = startCalendar.getTime();

            }
            //
            if (currentTime.before(startTime)) {

                System.out.println(" Time is Lesser ");

                valid = false;
            } else {

                if (currentTime.after(endTime)) {
                    endCalendar.add(Calendar.DATE, 1);
                    endTime = endCalendar.getTime();

                }

                System.out.println("Comparing , Start Time /n " + startTime);
                System.out.println("Comparing , End Time /n " + endTime);
                System.out
                        .println("Comparing , Current Time /n " + currentTime);

                if (currentTime.before(endTime)) {
                    System.out.println("RESULT, Time lies b/w");
                    valid = true;
                } else {
                    valid = false;
                    System.out.println("RESULT, Time does not lies b/w");
                }

            }
            return valid;

        } else {
            throw new IllegalArgumentException(
                    "Not a valid time, expecting HH:MM:SS format");
        }

    }


    public static String LookupProductPrice(Product product, Pub pub){

        String ans = "N/A";

        for(Price i : pub.prices.priceList){

            if(DoProductsMatch(i.product, product)){
                ans = String.valueOf(i.price);
            }
        }

        return ans;
    }

    public static  boolean DoProductsMatch(Product one, Product two){

        if(one.brand == two.brand && one.type == two.type && one.amount == two.amount ){
            return true;
        }
        return false;

    }

    public static Price FindProductinPub(Product prod, Pub pub){

        Price ans = null;

        for(Price p : pub.prices.priceList){

            if(DoProductsMatch(p.product,prod)){

                ans = p;

            }

        }
        return ans;
    }

    public static ArrayList<Pub> FindPubsWithProduct(Product prod){

        ArrayList<Pub> list = new ArrayList<Pub>();

        PubSetup setup = new PubSetup();

        for(int i=0;i<setup.pubs.size();i++){

            Pub thisPub = setup.pubs.get(i);

            for(int j=0;j<thisPub.prices.priceList.size();j++){

                Product thisProduct = thisPub.prices.priceList.get(j).product;

                if(HelperMethods.DoProductsMatch(prod, thisProduct)){

                    list.add(thisPub);
                }
            }
        }

        return list;
    }




}
