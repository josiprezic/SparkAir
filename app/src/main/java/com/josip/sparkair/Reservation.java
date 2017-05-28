package com.josip.sparkair;

import java.util.Calendar;

/**
 * Created by Josip on 28.5.2017..
 */

public class Reservation {
    private int userID;
    private int flightID;
    private Calendar date;
    private boolean active;

    public Reservation(int userID, int flightID, Calendar date, boolean active) {
        this.userID = userID;
        this.flightID = flightID;
        this.date = date;
        this.active = active;
    }

    public Reservation() {
    }


    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getFlightID() {
        return flightID;
    }

    public void setFlightID(int flightID) {
        this.flightID = flightID;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}


