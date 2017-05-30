package com.josip.sparkair;

import java.util.Calendar;

/**
 * Created by Josip on 28.5.2017..
 */

public class MyLog {
    private int logID;
    private int userID;
    private String action;
    private Calendar dateTime;

    public MyLog(int logID, int userID, String action, Calendar dateTime) {
        this.logID = logID;
        this.userID = userID;
        this.action = action;
        this.dateTime = dateTime;
    }

    public MyLog() {
    }

    public int getLogID() {
        return logID;
    }

    public void setLogID(int logID) {
        this.logID = logID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Calendar getDateTime() {
        return dateTime;
    }

    public void setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
    }
}
