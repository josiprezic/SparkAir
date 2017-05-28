package com.josip.sparkair;

/**
 * Created by Josip on 28.5.2017..
 */

public class Note {
    private int flightID;
    private int userID;
    private String content;

    public Note(int flightID, int userID, String content) {
        this.flightID = flightID;
        this.userID = userID;
        this.content = content;
    }

    public Note() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
