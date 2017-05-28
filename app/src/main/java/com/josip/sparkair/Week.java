package com.josip.sparkair;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Josip on 28.5.2017..
 */

public class Week {
    private int weekID;
    private Calendar startDate;
    private Calendar endDate;

    public Week(int weekID, Calendar startDate, Calendar endDate) {
        this.weekID = weekID;
        this.startDate = startDate;
        this.endDate = endDate;

    }

    public Week() {}

    public int getWeekID() {
        return weekID;
    }

    public void setWeekID(int weekID) {
        this.weekID = weekID;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }
}


