package com.josip.sparkair;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Josip on 28.5.2017..
 */


public class DatabaseHelper  extends SQLiteOpenHelper {

    //Names of database, versions, tables and columns
    private static final String DATABASE_NAME = "SparkAir.db";
    private static final int VERSION = 4;

    //TABLE USERS
    public static final String USERS_TABLE = "users";
    public static final String USER_ID = "_id";
    public static final String USER_USERNAME = "username";
    public static final String USER_PASSWORD = "password";
    public static final String USER_NAME = "name";
    public static final String USER_SURNAME = "surname";
    public static final String USER_IMAGE = "image";
    public static final String USER_ACTIVE = "active";
    public static final String USER_TYPE = "type";

    //TABLE WEEKS
    public static final String WEEKS_TABLE = "weeks";
    public static final String WEEK_ID = "_id";
    public static final String WEEK_STARTDATE = "startdate";
    public static final String WEEK_ENDDATE = "enddate";

    //TABLE FLIGHTS
    public static final String FLIGHTS_TABLE = "flights";
    public static final String FLIGHT_ID = "_id";
    public static final String FLIGHT_USER_ID = "user_id";
    public static final String FLIGHT_WEEK_ID = "week_id";
    public static final String FLIGHT_DESTINATION = "destination";
    public static final String FLIGHT_DATE = "date";
    public static final String FLIGHT_TIME = "time";
    public static final String FLIGHT_PRICE = "price";
    public static final String FLIGHT_ACTIVE = "active";

    //TABLE NOTES
    public static final String NOTES_TABLE = "notes";
    public static final String NOTE_ID = "_id";
    public static final String NOTE_USER_ID = "user_id";
    public static final String NOTE_CONTENT = "content";

    //TABLE RESERVATIONS
    public static final String RESERVATIONS_TABLE = "reservations";
    public static final String RESERVATION_ID = "_id";
    public static final String RESERVATION_FLIGHT_ID = "flight_id";
    public static final String RESERVATION_DATE = "date";
    public static final String RESERVATION_ACTIVE = "active";

    //TABLE LOGS
    public static final String LOGS_TABLE = "logs";
    public static final String LOG_ID = "_id";
    public static final String LOG_USER_ID = "user_id";
    public static final String LOG_ACTION = "action";
    public static final String LOG_DATE = "date";
    public static final String LOG_TIME = "time";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,  null, VERSION);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Creating table users
        db.execSQL("CREATE TABLE " + USERS_TABLE + " ( " +
                USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USER_USERNAME + " TEXT," +
                USER_PASSWORD + " TEXT," +
                USER_NAME + " TEXT," +
                USER_SURNAME + " TEXT," +
                USER_IMAGE + " TEXT," +
                USER_ACTIVE + " INTEGER," +
                USER_TYPE + " INTEGER)");

        //Creating table weeks
        db.execSQL("CREATE TABLE " + WEEKS_TABLE + " ( " +
                WEEK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                WEEK_STARTDATE + " TEXT," +
                WEEK_ENDDATE + " TEXT)");

        //Creating table flights
        db.execSQL("CREATE TABLE " + FLIGHTS_TABLE + " ( " +
                FLIGHT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FLIGHT_USER_ID + " INTEGER," +
                FLIGHT_WEEK_ID + " INTEGER," +
                FLIGHT_DESTINATION + " TEXT," +
                FLIGHT_DATE + " TEXT," +
                FLIGHT_TIME + " TEXT," +
                FLIGHT_PRICE + " REAL," +
                FLIGHT_ACTIVE + " INTEGER)");

        //Creating table notes
        db.execSQL("CREATE TABLE " + NOTES_TABLE + " ( " +
                NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NOTE_USER_ID + " INTEGER," +
                NOTE_CONTENT + " TEXT)");

        //Creating table reservations
        db.execSQL("CREATE TABLE " + RESERVATIONS_TABLE + " ( " +
                RESERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RESERVATION_FLIGHT_ID + " INTEGER," +
                RESERVATION_DATE + " TEXT," +
                RESERVATION_ACTIVE + " INTEGER)");

        //Creating table logs
        db.execSQL("CREATE TABLE " + LOGS_TABLE + " ( " +
                LOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                LOG_USER_ID + " INTEGER," +
                LOG_ACTION + " TEXT," +
                LOG_DATE + " TEXT," +
                LOG_TIME + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop all tables and create them again
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WEEKS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FLIGHTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RESERVATIONS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LOGS_TABLE);
        onCreate(db);
    }

    /*******************************************USER METHODS*************************************/


    public boolean insertUser(String name, String surname, String username, String password){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_USERNAME, username);
        contentValues.put(USER_PASSWORD, password);
        contentValues.put(USER_NAME, name);
        contentValues.put(USER_SURNAME, surname);
        contentValues.put(USER_ACTIVE, 1);
        contentValues.put(USER_IMAGE, "slika");
        long result = db.insert(USERS_TABLE, null, contentValues);
        db.close();
        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    //Returns all users data
    //public Cursor getAllUsers() {
    //    SQLiteDatabase db = this.getWritableDatabase();
    //    Cursor c = db.rawQuery("SELECT " + USER_ID + ", " + USER_USERNAME + ", " + USER_PASSWORD + ", " + USER_NAME + ", " +
    //           USER_SURNAME + ", " + USER_IMAGE + "," +USER_ACTIVE + ", " + USER_TYPE + " FROM " + USERS_TABLE, null);
//
    //   // Cursor c = db.rawQuery("select * from " + USERS_TABLE, null);
 //       return c;
   // }



    //Funckija vraca listu sa svim userima i svim njihovim informacijama
    public ArrayList<User> getAllusers() {


        ArrayList<User> list = new ArrayList<User>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + USERS_TABLE, null);

        if(c!= null && c.getCount() > 0) {
            while(c.moveToNext()) {
                list.add(new User(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getInt(6) == 1, c.getInt(7)));
            }
        }
        return list;
    }


    //funkcija trazi korisnika na osnovu usernamea i passworda te vraca usera sa svim informacijama
    public User getUserInfo(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        //String[] selectionArgs = new String[]{username, password};
        Cursor c = null;
        c = db.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE " + USER_USERNAME + " LIKE '" + username + "' AND " + USER_PASSWORD + " LIKE '" + password +"'  ", null);
        //db.close();
        if(c!= null && c.getCount() > 0){
            c.moveToNext();
            //User w  = new User(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getInt(5), c.getInt(6) == 1, c.getInt(7));
            User u = new User(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getInt(6) == 1, c.getInt(7));
            return  u;
        }
        return new User();
    }

    /*******************************************WEEK METHODS*************************************/

    //Spremanje week-a u bazu
    public boolean insertWeek(Week week)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WEEK_STARTDATE, CalendarToString(week.getStartDate()));
        contentValues.put(WEEK_ENDDATE, CalendarToString(week.getEndDate()));
        long result = db.insert(WEEKS_TABLE, null, contentValues);
        db.close();
        if(result == -1){
            return false;
        } else {
            return true;
        }
    }


    public ArrayList<Week> getAllWeeks() {
        ArrayList<Week> list = new ArrayList<Week>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + WEEKS_TABLE, null);

        if(c!= null && c.getCount() > 0) {
            while(c.moveToNext()) {
                list.add(new Week(c.getInt(0), StringToCalendar(c.getString(1)), StringToCalendar(c.getString(2))));
                //list.add(new Week(c.getInt(0), c.getString(1), c.getString(2)));
            }
        }
        return list;
    }

    /*******************************************FLIGHT METHODS***********************************/



    /*******************************************NOTE METHODS*************************************/
    /*******************************************RESERVATION METHODS******************************/
    /*******************************************LOG METHODS**************************************/


    /*****************************************OTHER METHODS**************************************/

    //Vraca String u formatu YYYY-MM-DD-HH-MM
    //Pripaziti na vrijednost MONTH(0-11) i DAY(SUNDAY == 1)
    private String CalendarToString(Calendar c) {

        //Potrebno kako bi se preracunale vrijednosti
        c.get(Calendar.WEEK_OF_YEAR);

        String y = String.valueOf(c.get(Calendar.YEAR));
        String month = String.valueOf(c.get(Calendar.MONTH));
        String d = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String h = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(c.get(Calendar.MINUTE));

        String result = y + "-" + month + "-" + d + "-" + h + "-" + minute;
        return  result;
    }

    //Prima string u formatu YYYY-MM-DD-HH-MM i vraca objekat tipa Calendar
    //Pripaziti na vrijednost MONTH(0-11) i DAY(SUNDAY == 1)
    private Calendar StringToCalendar(String s) {
        String[] odvojeno = s.split("-");
        int[] vrijednosti = new int[5];
        for (int i = 0; i < 5; i++) {
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



}