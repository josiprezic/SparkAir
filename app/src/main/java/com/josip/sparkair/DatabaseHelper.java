package com.josip.sparkair;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.josip.sparkair.Util.Util;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Josip on 28.5.2017..
 */


public class DatabaseHelper  extends SQLiteOpenHelper {

    //Names of database, versions, tables and columns
    private static final String DATABASE_NAME = "SparkAir.db";
    private static final int VERSION = 1;

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

    //TABLE FLIGHTS
    public static final String FLIGHTS_TABLE = "flights";
    public static final String FLIGHT_ID = "_id";
    public static final String FLIGHT_USER_ID = "user_id";
    public static final String FLIGHT_DESTINATION = "destination";
    public static final String FLIGHT_DATE_TIME = "date_time";
    public static final String FLIGHT_PRICE = "price";
    public static final String FLIGHT_ACTIVE = "active";

    //TABLE NOTES
    public static final String NOTES_TABLE = "notes";
    public static final String NOTE_FLIGHT_ID = "flight_id";
    public static final String NOTE_USER_ID = "user_id";
    public static final String NOTE_CONTENT = "content";

    //TABLE RESERVATIONS
    public static final String RESERVATIONS_TABLE = "reservations";
    public static final String RESERVATION_USER_ID = "user_id";
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

        //Creating table flights
        db.execSQL("CREATE TABLE " + FLIGHTS_TABLE + " ( " +
                FLIGHT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FLIGHT_USER_ID + " INTEGER," +
                FLIGHT_DESTINATION + " TEXT," +
                FLIGHT_DATE_TIME + " TEXT," +
                FLIGHT_PRICE + " REAL," +
                FLIGHT_ACTIVE + " INTEGER)"); //" +
                //" FOREIGN KEY(" + FLIGHT_USER_ID + ") REFERENCES " + USERS_TABLE + "(" + USER_ID + "))");

        //Creating table notes
        db.execSQL("CREATE TABLE " + NOTES_TABLE + " ( " +
                NOTE_FLIGHT_ID + " INTEGER PRIMARY KEY," +
                NOTE_USER_ID + " INTEGER," +
                NOTE_CONTENT + " TEXT)");

        //Creating table reservations
        db.execSQL("CREATE TABLE " + RESERVATIONS_TABLE + " ( " +
                RESERVATION_USER_ID + " INTEGER," +
                RESERVATION_FLIGHT_ID + " INTEGER," +
                RESERVATION_DATE + " TEXT," +
                RESERVATION_ACTIVE + " INTEGER," +
                " PRIMARY KEY (" + RESERVATION_USER_ID + ", " + RESERVATION_FLIGHT_ID + "))");

        //Creating table logs
        db.execSQL("CREATE TABLE " + LOGS_TABLE + " ( " +
                LOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                LOG_USER_ID + " INTEGER," +
                LOG_ACTION + " TEXT," +
                LOG_DATE + " TEXT," +
                LOG_TIME + " TEXT)");
    }

    //Izvrsava se prilikom promjene modela baze
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop all tables and create them again
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FLIGHTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + NOTES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RESERVATIONS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LOGS_TABLE);
        onCreate(db);
    }


    /*******************************************USER METHODS***************************************/
    //Insert novih usera
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

    //Funkcija vraca listu sa svim userima i svim njihovim informacijama
    public ArrayList<User> getAllusers() {
        ArrayList<User> list = new ArrayList<User>();
        SQLiteDatabase db = this.getWritableDatabase();

        //Dobavljanje svih usera
        Cursor c = db.rawQuery("SELECT * FROM " + USERS_TABLE, null);

        //Ubacivanje usera u listu
        if(c!= null && c.getCount() > 0) {
            while(c.moveToNext()) {
                list.add(new User(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getInt(6) == 1, c.getInt(7)));
            }
        }
        return list;
    }

    //Funkcija trazi korisnika na osnovu usernamea i passworda te vraca usera sa svim informacijama
    public User getUserInfo(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
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


    /*******************************************FLIGHT METHODS*************************************/
    //Insert novih flight-ova
    public boolean insertFlight(int userID, String destination, Calendar dateTime, double price, boolean active) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FLIGHT_USER_ID, userID);
        contentValues.put(FLIGHT_DESTINATION, destination);
        contentValues.put(FLIGHT_DATE_TIME, Util.CalendarToString(dateTime));
        contentValues.put(FLIGHT_PRICE, price);
        contentValues.put(FLIGHT_ACTIVE, active?1:0);

        long result = db.insert(FLIGHTS_TABLE, null, contentValues);
        db.close();
        if(result == -1)
            return false;
        return true;
    }

    //Dobavljanje svih flight-ova
    public ArrayList<Flight> getAllFlights() {
        ArrayList<Flight> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        //Dobavljenje svih flight-ova
        Cursor c = db.rawQuery("SELECT * FROM " + FLIGHTS_TABLE, null);

        //Ubacivanje flight-ova u listu
        if(c!= null && c.getCount() > 0) {
            while(c.moveToNext()) {
                list.add(new Flight(c.getInt(0), c.getInt(1), c.getString(2), Util.StringToCalendar(c.getString(3)), c.getFloat(4), c.getInt(5) == 1));
            }
        }
        return list;
    }

    /*******************************************NOTE METHODS***************************************/
    //Insert novih notesa
    public boolean insertNote(int flightID, int userID, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_FLIGHT_ID, flightID);
        contentValues.put(NOTE_USER_ID, userID);
        contentValues.put(NOTE_CONTENT, content);

        long result = db.insert(NOTES_TABLE, null, contentValues);
        db.close();
        if(result == -1)
            return false;
        return true;
    }


        /*
        *
        *
    public static final String NOTES_TABLE = "notes";
    public static final String NOTE_FLIGHT_ID = "flight_id";
    public static final String NOTE_USER_ID = "user_id";
    public static final String NOTE_CONTENT = "content";


     //Creating table notes
        db.execSQL("CREATE TABLE " + NOTES_TABLE + " ( " +
                NOTE_FLIGHT_ID + " INTEGER PRIMARY KEY," +
                NOTE_USER_ID + " INTEGER," +
                NOTE_CONTENT + " TEXT)");


                 public Note(int flightID, int userID, String content) {
        * */

    ArrayList<Note> getAllNotes() {
        ArrayList<Note> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        //Dobavljenje svih notesa
        Cursor c = db.rawQuery("SELECT * FROM " + NOTES_TABLE, null);

        //Ubacivanje notesa u listu
        if(c!= null && c.getCount() > 0) {
            while(c.moveToNext()) {
                list.add(new Note(c.getInt(0), c.getInt(1), c.getString(2)));
            }
        }
        return list;
    }


    /*******************************************RESERVATION METHODS******************************/
    /*******************************************LOG METHODS**************************************/


    /*****************************************OTHER METHODS**************************************/

}