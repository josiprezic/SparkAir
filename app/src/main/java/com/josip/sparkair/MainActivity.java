package com.josip.sparkair;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TabHost tabHost;
    DatabaseHelper myDb;
    ListView lvIduciLetovi;// = (ListView) findViewById(R.id.main_lvIduciLetovi);
    public TextView tvBaza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        lvIduciLetovi = (ListView) findViewById(R.id.main_lvIduciLetovi);
        tvBaza = (TextView) findViewById(R.id.tvBaza);

        //Provjera koji je user logovan
        User currentUser = getCurrentUser();
        tvBaza.setText("Current user: " +  Integer.toString(currentUser.getUserID()) + ", " + currentUser.getUsername() + ", " + currentUser.getPassword() + "\n");


        //Postavnjanje tabTostova
        TabHost host = (TabHost) findViewById(R.id.tabHost);
        host.setup();

        //Tab Top Ponuda
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tabTopPonuda);
        spec.setIndicator("Top ponuda");
        host.addTab(spec);

        //Tab Iduci letovi
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tabIduciLetovi);
        spec.setIndicator("Idući letovi");
        host.addTab(spec);

        //Postavljanje toolbara
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Dugme s postom na dnu ekrana + snackbar
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //myDb.insertUser("TestIme", "TestPrezime", "TestUSername", "TestPassword");


        //Postavljanje custom adaptera za iduce letove
        final Flight[] flights = new Flight[20];
        for (int i = 0; i < 20; i++) {
            flights[i] = new Flight(1, 2, 3, "Barcelona", Calendar.getInstance(), 200);
        }

        ListAdapter iduciLetoviAdapter = new CustomAdapterIduciLetovi(this, flights);
        lvIduciLetovi.setAdapter(iduciLetoviAdapter);
        lvIduciLetovi.setClickable(true);

        lvIduciLetovi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Flight flight = flights[position];
                Toast.makeText(getApplicationContext(), "RADI!", Toast.LENGTH_SHORT).show();

            }
        });

    }






    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.actionSignIn) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navPocetna) {
            // Handle the camera action
        } else if (id == R.id.navBuducaPutovanja) {

        } else if (id == R.id.navProšlaPutovanja) {

        } else if (id == R.id.navKontakt) {

        }
//        else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();



        //TESTIRANJE FUNKCIJE getallusers sa listom
        ArrayList<User> users = myDb.getAllusers();

        if (users.size() == 0) {
            tvBaza.setText("\n" + tvBaza.getText().toString() + "Nema usera u bazi");
        } else {
            StringBuffer string = new StringBuffer();
            for (int i = 0; i < users.size(); i++) {
                string.append(Integer.toString(users.get(i).getUserID()) + "," + users.get(i).getName() + ", " + users.get(i).getSurname() + "\n");
            }
            tvBaza.setText("\n" + tvBaza.getText().toString() + "\n" + "Users: \n" + string.toString());
        }


        //testiranje WEEKS tabele
        Calendar start = Calendar.getInstance();
        start.set(2017, Calendar.MAY, 29, 23, 15);

        Calendar end = Calendar.getInstance();
        end.set(2017, Calendar.JUNE, 3, 20, 0);

        myDb.insertWeek(new Week(-1, start, end));

        List<Week> weeks = myDb.getAllWeeks();

        if (weeks.size() == 0) {
            tvBaza.setText("\n" + tvBaza.getText().toString() + "\n" + "Nema weekova u bazi");
        } else {
            StringBuffer string2 = new StringBuffer();
            for (int i = 0; i < weeks.size(); i++) {
                string2.append(Integer.toString(weeks.get(i).getWeekID()) + "," + weeks.get(i).getStartDateString() + ", " + weeks.get(i).getEndDateString() + "\n");

            }
            tvBaza.setText(tvBaza.getText().toString() +  "\nWeeks: \n" + string2.toString());

        }
    }

    public User getCurrentUser() {
        //Provjera da li je user shared preferences
        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        String username = settings.getString("username", "guest");
        String password = settings.getString("password", "guest");

        if(username.equals("guest")) {
          return new User(-1, "guest", "guest", "Guest", "Guest", "slika", true, -1);
        }
        //User currentUser =  myDb.getUserInfo(username, password);
        return myDb.getUserInfo(username, password);

        //Toast.makeText(getApplicationContext(), username + " " + password, Toast.LENGTH_SHORT).show();
    }


}