package com.josip.sparkair.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.josip.sparkair.DatabaseHelper;
import com.josip.sparkair.MainActivity;
import com.josip.sparkair.User;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Josip on 29.5.2017..
 */

public class Util {

    //Vraca string u obliku YYYY-MM-DD-HH-MM
    public static String CalendarToString(Calendar c) {
        String y = String.valueOf(c.get(Calendar.YEAR));
        String month = String.valueOf(c.get(Calendar.MONTH));
        String d = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String h = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(c.get(Calendar.MINUTE));
        return y + "-" + month + "-" + d + "-" + h + "-" + minute;
    }


    //Prima string u formatu YYYY-MM-DD-HH-MM i vraca objekat tipa Calendar
    //Pripaziti na vrijednost MONTH(0-11) i DAY(SUNDAY == 1)
    public static Calendar StringToCalendar(String s) {
        String[] odvojeno = s.split("-");
        int[] vrijednosti = new int[5];
        for (int i = 0; i < 5; i++)
        {
            vrijednosti[i] = Integer.valueOf(odvojeno[i]);
        }
        Calendar rezultat = Calendar.getInstance();
        rezultat.set(Calendar.YEAR, vrijednosti[0]);
        rezultat.set(Calendar.MONTH, vrijednosti[1]);
        rezultat.set(Calendar.DAY_OF_MONTH, vrijednosti[2]);
        rezultat.set(Calendar.HOUR_OF_DAY, vrijednosti[3]);
        rezultat.set(Calendar.MINUTE, vrijednosti[4]);

        //Potrebno kako bi se preracunale vrijednosti
        rezultat.get(Calendar.WEEK_OF_YEAR);
        return rezultat;
    }


    public static User getCurrentUser(Context context) {
        //Provjera da li je user shared preferences
        SharedPreferences settings = context.getSharedPreferences("UserInfo", 0);
        String username = settings.getString("username", "guest");
        String password = settings.getString("password", "guest");

        if(username.equals("guest")) {
            return new User(-1, "guest", "guest", "Guest", "Guest", "slika", true, -1);
        }

        DatabaseHelper myDb = new DatabaseHelper(context);
        return myDb.getUserInfo(username, password);

        //Toast.makeText(getApplicationContext(), username + " " + password, Toast.LENGTH_SHORT).show();
    }


}
