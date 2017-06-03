package com.josip.sparkair;

import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuInflater;
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

import com.josip.sparkair.Util.Util;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TabHost tabHost;
    DatabaseHelper myDb;
    ListView lvIduciLetovi;
    ListView lvTopPonudaLetovi;
    public TextView tvBaza;
    Menu navDrawerMenu;
    NavigationView navigationView;


    public static final String TRENUTNI_FRAGMENT = "trenutniFragment";
    public static final int POCETNA = R.id.navPocetna;
    public static final int BUDUCA_PUTOVANJA = R.id.navBuducaPutovanja;
    public static final int PROSLA_PUTOVANJA = R.id.navProšlaPutovanja;
    public static final int KONTAKT = R.id.navKontakt;
    public static final String TRENUTNI_TAB = "trenutniTab";
    private int currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        lvIduciLetovi = (ListView) findViewById(R.id.main_lvIduciLetovi);
        lvTopPonudaLetovi = (ListView) findViewById(R.id.lvTopPonudaLetovi);
        tvBaza = (TextView) findViewById(R.id.tvBaza);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        //Provjera koji je user logovan
        //User currentUser = Util.getCurrentUser(getApplicationContext());
        //tvBaza.setText("Current user: " +  Integer.toString(currentUser.getUserID()) + ", " + currentUser.getUsername() + ", " + currentUser.getPassword() + "\n");

        //Postavnjanje tabTostova
        //TabHost
        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        //Tab Top Ponuda
        TabHost.TabSpec spec = tabHost.newTabSpec("Tab One");
        spec.setContent(R.id.tabTopPonuda);
        spec.setIndicator("Top ponuda");
        tabHost.addTab(spec);

        //Tab Iduci letovi
        spec = tabHost.newTabSpec("Tab Two");
        spec.setContent(R.id.tabIduciLetovi);
        spec.setIndicator("Idući letovi");
        tabHost.addTab(spec);

        //Tab Test
        spec = tabHost.newTabSpec("Tab Three");
        spec.setContent(R.id.tabTest);
        spec.setIndicator("Test");
        tabHost.addTab(spec);

        //Dohvaćanje spremljenih podataka nakon rotacije screena
        // i postavljanje trenutno otvorenog fragmenta i ukljucivanje i iskljucivanje tabhosta
        if(savedInstanceState != null) {
            currentFragment = savedInstanceState.getInt(TRENUTNI_FRAGMENT);
            setSpremljeniFragment(currentFragment);
        }
        else {
            currentFragment = POCETNA;
        }

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

        //Postavljanje custom adaptera za TopPonuda letove
        final Flight[] topFlights = new Flight[20];
        for (int i = 0; i < 20; i++) {
            topFlights[i] = new Flight(1, 2, "Pariz", Calendar.getInstance(), 400, true);
        }

        ListAdapter iduciLetoviAdapter1 = new CustomAdapterFlights(this, topFlights);
        lvTopPonudaLetovi.setAdapter(iduciLetoviAdapter1);
        lvTopPonudaLetovi.setClickable(true);

        //Ono sto ce se desiti na klik itema sa liste
        lvTopPonudaLetovi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Flight flight = topFlights[position];
            }
        });

        //Postavljanje custom adaptera za iduce letove
        final Flight[] flights = new Flight[20];
        for (int i = 0; i < 20; i++) {
            flights[i] = new Flight(1, 2, "Barcelona", Calendar.getInstance(), 200, true);
        }

        ListAdapter iduciLetoviAdapter = new CustomAdapterFlights(this, flights);
        lvIduciLetovi.setAdapter(iduciLetoviAdapter);
        lvIduciLetovi.setClickable(true);

        //Sta ce se desiti na klik itema sa liste
        lvIduciLetovi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Flight flight = flights[position];
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


    //Menu u gornjem desnom kutu
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        //Provjera da li je user logovan i ako jest prikazati opciju logOut
        //Ako nije logovan prikazati opciju login
        User user = Util.getCurrentUser(getApplicationContext());
        Menu nav_Menu = navigationView.getMenu(); //dravable menu

        if (user.getUserID() == -1) {
            //trenutni user je gost, prikazati samo login opciju
            menu.findItem(R.id.actionLogOut).setVisible(false);
            menu.findItem(R.id.actionSignIn).setVisible(true);
            //Uredjivanje opcije u navigation draweru
            nav_Menu.findItem(R.id.nav_odjava).setVisible(false);

        }
        else {
            //user je logiran, prikazati samo logout opciju
            menu.findItem(R.id.actionLogOut).setVisible(true);
            menu.findItem(R.id.actionSignIn).setVisible(false);
            //Uredjivanje opcije u navigation draweru
            nav_Menu.findItem(R.id.nav_odjava).setVisible(true);
        }
        invalidateOptionsMenu();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        navDrawerMenu = menu;
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
        else if (id == R.id.actionLogOut) {
            Util.odjaviSe(getApplicationContext());
            Toast.makeText(this, "Odjavili ste se", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Prikaz odabranog fragmenta
    private void displaySelectedScreen(int id) {
        Fragment fragment = null;

        switch (id) {
            case POCETNA:
                currentFragment = POCETNA;
                break;
            case BUDUCA_PUTOVANJA:
                fragment = new MenuBuducaPutovanja();
                currentFragment = BUDUCA_PUTOVANJA;
                break;
            case PROSLA_PUTOVANJA:
                fragment = new MenuProslaPutovanja();
                currentFragment = PROSLA_PUTOVANJA;
                break;
            case KONTAKT:
                fragment = new MenuKontakt();
                currentFragment = KONTAKT;
                break;
        }

        if (fragment != null) {
          //  currentFragment = id;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    //Odabir itema sa menija
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_odjava) {
            Util.odjaviSe(getApplicationContext());
            id = R.id.navPocetna;
        }
        if (id == R.id.navPocetna) {
            tabHost.setVisibility(View.VISIBLE);
            tabHost.setCurrentTab(0);
        }
        else {
            tabHost.setVisibility(View.GONE);
        }
        displaySelectedScreen(id);
        return true;
    }

    //Samo radi testiranja
    @Override
    protected void onResume() {
        super.onResume();
        testiranje();
    }

    //Izbrisati kasnije
    public void testiranje() {

        //Provjera koji je user logovan
        User currentUser = Util.getCurrentUser(getApplicationContext());
        Toast.makeText(this, "Current user:" + Integer.toString(currentUser.getUserID()), Toast.LENGTH_SHORT).show();
        tvBaza.setText("Current user: " +  Integer.toString(currentUser.getUserID()) + ", " + currentUser.getUsername() + ", " + currentUser.getPassword() + "\n");

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

        //Testiranje flightova
        myDb.insertFlight(2, "Barcelona", Calendar.getInstance(), 200, true);
        ArrayList<Flight> flights = myDb.getAllFlights();
        tvBaza.setText(tvBaza.getText().toString() + "\n" + "Letovi:\n");
        for (int i = 0; i < flights.size(); i++) {
            tvBaza.setText(tvBaza.getText().toString() + "\n" + Integer.toString(flights.get(i).getFlightID()) + ", " + Integer.toString(flights.get(i).getUserID()) + "," + flights.get(i).getDestination() );
        }

        //Testoramke notesa
        myDb.insertNote(2, 3, "Ovo je sadrzaj biljeske.");
        ArrayList<Note> notes = myDb.getAllNotes();
        tvBaza.setText(tvBaza.getText().toString() + "\n" + "Nostesi:\n");
        for (int i = 0; i < notes.size(); i++) {
            tvBaza.setText(tvBaza.getText().toString() + "\n" + Integer.toString(notes.get(i).getFlightID()) + ", " + Integer.toString(notes.get(i).getUserID()) + "," + notes.get(i).getContent() );
        }

        //Testiranje rezervacija
        myDb.insertReservation(3, 2, Calendar.getInstance(), true);
        ArrayList<Reservation> reservations = myDb.getAllReservations();
        tvBaza.setText(tvBaza.getText().toString() + "\n" + "Rezervacije:\n");
        for (int i = 0; i < reservations.size(); i++) {
            tvBaza.setText(tvBaza.getText().toString() + "\n" + Integer.toString(reservations.get(i).getUserID()) + ", " + Integer.toString(reservations.get(i).getFlightID()) + "," + Util.CalendarToString(reservations.get(i).getDate()) + " ," + String.valueOf(reservations.get(i).isActive()));
        }

        //Testiranje logova
      boolean uspjesno =  myDb.insertLog(1, "User je rezervirao putovanje u Barcelonu", Calendar.getInstance());
       ArrayList<MyLog> logs = myDb.getAllLogs();
        tvBaza.setText(tvBaza.getText().toString() + "\n" + "\nLogovi(" + Integer.toString(logs.size()) + "):\n");
        for (int i = 0; i < logs.size(); i++) {
            tvBaza.setText(tvBaza.getText().toString() + "\n" + Integer.toString(logs.get(i).getLogID()) + ", " + Integer.toString(logs.get(i).getUserID()) + "," + Util.CalendarToString(logs.get(i).getDateTime()) + " ," + String.valueOf(logs.get(i).getAction()));
        }

      // host.setCurrentTab(2);
    }


    //Spremanje informacija u slucaju rotacije screena
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt(TRENUTNI_FRAGMENT, currentFragment);
        outState.putInt(TRENUTNI_TAB, tabHost.getCurrentTab());
        super.onSaveInstanceState(outState);

    }

    //Vraca otvoreni trenutni tab nakon rotacije screena
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            tabHost.setCurrentTab(savedInstanceState.getInt(TRENUTNI_TAB));
        }
    }

    //Pokrece spremljeni fragment nakon rotacije screena
    private void setSpremljeniFragment(int spremljeniFragmentID) {
        Fragment fragment;
        switch (spremljeniFragmentID)  {
            case POCETNA:
                tabHost.setVisibility(View.VISIBLE);
                currentFragment = POCETNA;
                return;
            //break;
            case BUDUCA_PUTOVANJA:
                fragment = new MenuBuducaPutovanja();
                currentFragment = BUDUCA_PUTOVANJA;
                break;
            case PROSLA_PUTOVANJA:
                fragment = new MenuProslaPutovanja();
                currentFragment = PROSLA_PUTOVANJA;
                break;
            case KONTAKT:
                fragment = new MenuKontakt();
                currentFragment = KONTAKT;
                break;
            default:
                fragment = new MenuBuducaPutovanja();
                break;

        }
        tabHost.setVisibility(View.GONE);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_main, fragment);
        ft.commit();
    }

    private void hideItem()
    {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_odjava).setVisible(false);
    }


}