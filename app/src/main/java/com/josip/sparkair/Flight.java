package com.josip.sparkair;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by Josip on 28.5.2017..
 */

public class Flight{
    private int flightID;
    private int userID;
    private String destination;
    private Calendar dateTime;
    private double price;
    private boolean active;
    private int brojRezervacija;


    public Flight(int flightID, int userID, String destination, Calendar dateTime, double price, boolean active) {
        this.flightID = flightID;
        this.userID = userID;
        this.destination = destination;
        this.dateTime = dateTime;
        this.price = price;
        this.active = active;
        this.brojRezervacija = 0;
    }


    public Flight(int flightID, int userID, String destination, Calendar dateTime, double price, boolean active, int brojRezervacija) {
        this.flightID = flightID;
        this.userID = userID;
        this.destination = destination;
        this.dateTime = dateTime;
        this.price = price;
        this.active = active;
        this.brojRezervacija = brojRezervacija;
    }


    public Flight() {
        this.flightID = -1;
        this.userID = -1;
        this.destination = "(empty)";
        this.dateTime = Calendar.getInstance();
        this.price = -1;
        this.active = false;
        this.brojRezervacija = 0;
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


    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Calendar getDateTime() {
        return dateTime;
    }

    public void setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getBrojRezervacija() {
        return brojRezervacija;
    }

    public void setBrojRezervacija(int brojRezervacija) {
        this.brojRezervacija = brojRezervacija;
    }

    public String getDateString() {
        dateTime.get(Calendar.WEEK_OF_YEAR);

        String y = String.valueOf(dateTime.get(Calendar.YEAR));
        // String month = String.valueOf(startDate.get(Calendar.MONTH));
        String month = dateTime.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
        String d = String.valueOf(dateTime.get(Calendar.DAY_OF_MONTH));
        String result = y + "-" + month + "-" + d;
        return  result;
    }

    public String getTimeString() {
        dateTime.get(Calendar.WEEK_OF_YEAR);

        String h = String.valueOf(dateTime.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(dateTime.get(Calendar.MINUTE));
        String result =  h + ":" + minute;
        return  result;
    }


}
