package com.josip.sparkair;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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


    public String getStartDateString() {
        startDate.get(Calendar.WEEK_OF_YEAR);

        String y = String.valueOf(startDate.get(Calendar.YEAR));
       // String month = String.valueOf(startDate.get(Calendar.MONTH));
        String month = startDate.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
        String d = String.valueOf(startDate.get(Calendar.DAY_OF_MONTH));
        String h = String.valueOf(startDate.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(startDate.get(Calendar.MINUTE));
        String result = y + "-" + month + "-" + d + "-" + h + "-" + minute;
        return  result;
    }

    public String getEndDateString() {
        endDate.get(Calendar.WEEK_OF_YEAR);

        String y = String.valueOf(endDate.get(Calendar.YEAR));
       // String month = String.valueOf(endDate.get(Calendar.MONTH));
        String month = startDate.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
        String d = String.valueOf(endDate.get(Calendar.DAY_OF_MONTH));
        String h = String.valueOf(endDate.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(endDate.get(Calendar.MINUTE));
        String result = y + "-" + month + "-" + d + "-" + h + "-" + minute;
        return  result;
    }
}


