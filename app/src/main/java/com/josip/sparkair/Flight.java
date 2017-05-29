package com.josip.sparkair;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Josip on 28.5.2017..
 */

public class Flight {
    private int flightID;
    private int userID;
    private int weekID;
    private String destination;
    private Calendar time;
    private double price;


    public Flight(int flightID, int userID, int weekID, String destination, Calendar time, double price) {
        this.flightID = flightID;
        this.userID = userID;
        this.weekID = weekID;
        this.destination = destination;
        this.time = time;
        this.price = price;
    }


    public Flight() {
    }

    public int getFlightID() {
        return flightID;
    }

    public void setFlightID(int flightID) {
        this.flightID = flightID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getWeekID() {
        return weekID;
    }

    public void setWeekID(int weekID) {
        this.weekID = weekID;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDateString() {
        time.get(Calendar.WEEK_OF_YEAR);

        String y = String.valueOf(time.get(Calendar.YEAR));
        // String month = String.valueOf(startDate.get(Calendar.MONTH));
        String month = time.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
        String d = String.valueOf(time.get(Calendar.DAY_OF_MONTH));
        String result = y + "-" + month + "-" + d;
        return  result;
    }

    public String getTimeString() {
        time.get(Calendar.WEEK_OF_YEAR);

        String h = String.valueOf(time.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(time.get(Calendar.MINUTE));
        String result =  h + ":" + minute;
        return  result;
    }
}
