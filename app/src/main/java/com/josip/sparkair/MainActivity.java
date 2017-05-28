package com.josip.sparkair;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TabHost tabHost;
    DatabaseHelper myDb;
    public TextView tvBaza;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        tvBaza = (TextView) findViewById(R.id.tvBaza);

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

        //TESTIRANJE BAZE -tabela user
        //String bazaKaze = "";
        //StringBuffer buffer = new StringBuffer();
        //buffer.append(" ");
        //int brojac = 0;
        //Cursor c  = myDb.getAllUsers();
        //if (c == null){
        //    Toast.makeText(getApplicationContext(), "Baza je prazna", Toast.LENGTH_SHORT).show();
        //}
        //else
        //{
        //   while (c.moveToNext()) {
        //        brojac++;
        //        buffer.append("Id: " + Integer.toString(c.getInt(0)) + " Username: " + c.getString(1) + "\n");
        //   }
        //}
        //
        //tvBaza.setText(buffer.toString());


        //TESTIRANJE DATUMA
       Calendar calendar = Calendar.getInstance();
       calendar.set(Calendar.YEAR, 2000);
       calendar.set(Calendar.MONTH, Calendar.JANUARY);
       calendar.set(Calendar.DAY_OF_MONTH, 1);

        int broj = Integer.valueOf("00000002");



       tvBaza.setText(String.valueOf(broj));

        String vrime = tvBaza.getText().toString();

        Calendar calendar1 = Calendar.getInstance();



        //TESTIRANJE FUNKCIJE getallusers sa listom
      // ArrayList<User> users = myDb.getAllusers();

      // StringBuffer string = new StringBuffer();
      // for (int i = 0; i < users.size(); i++) {
      //     string.append(Integer.toString(users.get(i).getUserID()) + "," + users.get(i).getName() + ", " +  users.get(i).getSurname() + "\n");
      // }

      // tvBaza.setText(string.toString());


    }
}
