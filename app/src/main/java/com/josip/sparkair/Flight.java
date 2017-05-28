package com.josip.sparkair;

import java.util.Calendar;

/**
 * Created by Josip on 28.5.2017..
 */

public class Flight {
    private int flightID;
    private int userID;
    private int weekID;
    private String destination;
    private Calendar time;


    public Flight(int flightID, int userID, int weekID, String destination, Calendar time) {
        this.flightID = flightID;
        this.userID = userID;
        this.weekID = weekID;
        this.destination = destination;
        this.time = time;
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
}
