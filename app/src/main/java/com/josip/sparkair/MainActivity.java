package com.josip.sparkair;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import com.josip.sparkair.Util.FlightComparator;
import com.josip.sparkair.Util.Util;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TabHost tabHost;
    DatabaseHelper myDb;
    ListView lvIduciLetovi;
    ListView lvTopPonudaLetovi;
    TextView tvBaza;
    Menu navDrawerMenu;
    NavigationView navigationView;
    User currentUser;
    ImageButton profileBox_btPostavke;
    ListAdapter TopPonudaLetoviAdapter;
    ListAdapter IduciLetoviAdapter;

    public static final String TRENUTNI_FRAGMENT = "trenutniFragment";
    public static final int POCETNA = R.id.navPocetna;
    public static final int BUDUCA_PUTOVANJA = R.id.navBuducaPutovanja;
    public static final int PROSLA_PUTOVANJA = R.id.navProšlaPutovanja;
    public static final int KONTAKT = R.id.navKontakt;
    public static final int ODJAVA = R.id.nav_odjava;
    public static final String TRENUTNI_TAB = "trenutniTab";
    private int currentFragment;
    DrawerLayout drawer;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //myDb.insertUser("AdminName", "AdminSurname", "admin", "admin", 2 );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //potrebno za rad s bazom
        myDb = new DatabaseHelper(this);

        //referenciranje listi za buduca i prosla putovanja
        lvIduciLetovi = (ListView) findViewById(R.id.main_lvIduciLetovi);
        lvTopPonudaLetovi = (ListView) findViewById(R.id.lvTopPonudaLetovi);
        //tv baza sluzi za testiranje kasnije se moze izbrisati
        tvBaza = (TextView) findViewById(R.id.tvBaza);
        //referenciranje navigation viewa
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        profileBox_btPostavke = (ImageButton) findViewById(R.id.profileBox_btPostavke);

        currentUser = Util.getCurrentUser(getApplicationContext());
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //FloatingButtonZaDodavanjeLetova
        fab = (FloatingActionButton) findViewById(R.id.fab);
        if (currentUser.getType() != 2) {
            fab.setVisibility(View.INVISIBLE);
        }

        //Postavljanje Tabova
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
        if (savedInstanceState != null) {
            currentFragment = savedInstanceState.getInt(TRENUTNI_FRAGMENT);
            setSpremljeniFragment(currentFragment);
        } else {
            currentFragment = POCETNA;
        }

        //Postavljanje toolbara
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Floating button za dodavanje novih letova
        fab.setOnClickListener(new View.OnClickListener() {
            //            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddFlightActivity.class));
            }
        });

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Trazi i prikazuje top ponuda letove
        final ArrayList<Flight> topFlights = getTopPonuda();
        TopPonudaLetoviAdapter = new CustomAdapterFlights(this, getTopPonuda());
        lvTopPonudaLetovi.setAdapter(TopPonudaLetoviAdapter);
        lvTopPonudaLetovi.setClickable(true);

        //Ono sto ce se desiti na klik itema sa liste
        lvTopPonudaLetovi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Flight flight = topFlights.get(position);
            }
        });

        //Postavljanje custom adaptera za iduce letove
        final ArrayList<Flight> flights = getTopPonuda();
        IduciLetoviAdapter = new CustomAdapterFlights(this, getIduciLetovi());
        lvIduciLetovi.setAdapter(IduciLetoviAdapter);
        lvIduciLetovi.setClickable(true);

        //Sta ce se desiti na klik itema sa liste
        lvIduciLetovi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Flight flight = flights.get(position);
            }
        });


    }


    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        // User user = Util.getCurrentUser(getApplicationContext()); --previse pozivanja


        Menu nav_Menu = navigationView.getMenu(); //dravable menu
        //Profile box - referenciranje objekata
        ImageView profileBox_ivProfilnaSlika = (ImageView) findViewById(R.id.profileBox_ivProfilnaSlika);
        TextView profileBox_tvImePrezime = (TextView) findViewById(R.id.profileBox_tvImePrezime);
        TextView profileBox_tvOstaleInformacije = (TextView) findViewById(R.id.profileBox_tvOstaleInformacije);

        //Postavljanje onClickListenera za profile box
        RelativeLayout profileBoxHeader = (RelativeLayout) findViewById(R.id.navProfileBox);
        profileBoxHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, "Kliknuo si na profile box", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                drawer.closeDrawer(GravityCompat.START);

            }
        });


        profileBox_tvImePrezime.setText(currentUser.getName() + " " + currentUser.getSurname());
        if (currentUser.getType() == -1) {
            //trenutni user je gost, prikazati samo login opciju
            menu.findItem(R.id.actionLogOut).setVisible(false);
            menu.findItem(R.id.actionSignIn).setVisible(true);
            //iskljucitanje opcije odjava u navigation draweru
            nav_Menu.findItem(R.id.nav_odjava).setVisible(false);
            //mijenjanje informacija u profile boxu
            profileBox_tvOstaleInformacije.setText("Guest account");
        } else {
            // user je logiran, prikazati samo logout opciju
            menu.findItem(R.id.actionLogOut).setVisible(true);
            menu.findItem(R.id.actionSignIn).setVisible(false);
            //ukljucivanje opcije logout u navigation draweru
            nav_Menu.findItem(R.id.nav_odjava).setVisible(true);
            //Mijenjanje informacija u profile boxu
            profileBox_tvOstaleInformacije.setText("Korisnik");
        }

        //Dodatne stvari za ADMIN account
        if (currentUser.getType() == 2) {
            //Mijenjanje informacija u profile boxu
            profileBox_tvOstaleInformacije.setText("ADMIN");
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


        if (id == R.id.actionSignIn) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.actionLogOut) {
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
        if (currentUser.getType() != 2) {
            fab.setVisibility(View.GONE);
        } else {
            fab.setVisibility(View.VISIBLE);
        }

        switch (id) {
            case POCETNA:
                currentFragment = POCETNA;
                fab.setVisibility(View.VISIBLE);
                break;
            case ODJAVA:
                currentFragment = POCETNA;

                fab.setVisibility(View.VISIBLE);
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
            Toast.makeText(this, "Odjavili ste se", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        if (id == R.id.navPocetna) {
            tabHost.setVisibility(View.VISIBLE);
            tabHost.setCurrentTab(0);
        } else {
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

        //Refresh liste Top Ponuda
        TopPonudaLetoviAdapter = new CustomAdapterFlights(this, getTopPonuda());
        lvTopPonudaLetovi.setAdapter(TopPonudaLetoviAdapter);

        //Refresh liste IduciLetovi
        IduciLetoviAdapter = new CustomAdapterFlights(this, getIduciLetovi());
        lvIduciLetovi.setAdapter(IduciLetoviAdapter);
    }

    //Izbrisati kasnije
    public void testiranje() {

        //Dodavanje admina
        myDb.insertUser("Administrator", "", "admin", "admin", 2);

        //Provjera koji je user logovan
        User currentUser = Util.getCurrentUser(getApplicationContext());
        // Toast.makeText(this, "Current user:" + Integer.toString(currentUser.getUserID()), Toast.LENGTH_SHORT).show();
        tvBaza.setText("Current user: " + Integer.toString(currentUser.getUserID()) + ", " + currentUser.getUsername() + ", " + currentUser.getPassword() + "\n" + "CurrentUserType: " + currentUser.getType() + "\n");

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
//
        Calendar buducnost = Calendar.getInstance();
        buducnost.add(Calendar.DAY_OF_MONTH, 1);

        Calendar zaGodinuiDana = Calendar.getInstance();
        buducnost.add(Calendar.YEAR, 1);

        Calendar proslost = Calendar.getInstance();
        proslost.add(Calendar.DAY_OF_MONTH, -1);

        //myDb.insertFlight(1, Util.getRandomString(), buducnost, 134.4 , true );
        //myDb.insertFlight(1, Util.getRandomString(), proslost, 124.4 , true );
        //myDb.insertFlight(1, Util.getRandomString(), zaGodinuiDana, 114.4 , true );

        ArrayList<Flight> flights = myDb.getAllFlights();
        tvBaza.setText(tvBaza.getText().toString() + "\n" + "Letovi:\n");
        for (int i = 0; i < flights.size(); i++) {
            tvBaza.setText(tvBaza.getText().toString() + "\n" + "FlightID: " + Integer.toString(flights.get(i).getFlightID()) +
                    ", UserID: " + Integer.toString(flights.get(i).getUserID()) +
                    ", Destination: " + flights.get(i).getDestination());
        }

        //Testiranje notesa
        myDb.insertNote(2, 3, "Ovo je sadrzaj biljeske.");
        ArrayList<Note> notes = myDb.getAllNotes();
        tvBaza.setText(tvBaza.getText().toString() + "\n" + "Notesi:\n");
        for (int i = 0; i < notes.size(); i++) {
            tvBaza.setText(tvBaza.getText().toString() + "\n" + Integer.toString(notes.get(i).getFlightID()) + ", " + Integer.toString(notes.get(i).getUserID()) + "," + notes.get(i).getContent());
        }

        //Testiranje rezervacija
        myDb.insertReservation(3, 2, Calendar.getInstance(), true);
        ArrayList<Reservation> reservations = myDb.getAllReservations();
        tvBaza.setText(tvBaza.getText().toString() + "\n" + "Rezervacije:\n");
        for (int i = 0; i < reservations.size(); i++) {
            tvBaza.setText(tvBaza.getText().toString() + "\n" + "UserID: " + Integer.toString(reservations.get(i).getUserID()) + ", Flight ID: " + Integer.toString(reservations.get(i).getFlightID()) + ", " + Util.CalendarToString(reservations.get(i).getDate()) + " , Aktivna: " + String.valueOf(reservations.get(i).isActive()));
        }


        //Testiranje logova
        boolean uspjesno = myDb.insertLog(1, "User je uspjesno rezervirao putovanje", Calendar.getInstance());
        ArrayList<MyLog> logs = myDb.getAllLogs();
        tvBaza.setText(tvBaza.getText().toString() + "\n" + "\nLogovi(" + Integer.toString(logs.size()) + "):\n");
        for (int i = 0; i < logs.size(); i++) {
            tvBaza.setText(tvBaza.getText().toString() + "\n" + "LogID: " + Integer.toString(logs.get(i).getLogID()) + ", UserID: " + Integer.toString(logs.get(i).getUserID()) + "," + Util.CalendarToString(logs.get(i).getDateTime()) + " ," + String.valueOf(logs.get(i).getAction()));
        }
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
        switch (spremljeniFragmentID) {
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

    public void onPostakeButtonClick(View view) {
        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
        drawer.closeDrawer(GravityCompat.START);
    }

    public void onReservationClicked(View view) {

        final ImageButton iduci_ibRezervacija = (ImageButton) findViewById(R.id.iduci_ibRezervacija);
        iduci_ibRezervacija.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){

                if(currentUser.getType() != -1) {

                    //Kmh ne radi? uvijek preskoči
                    //Da pokušamo sa setTag i getTag
                    if (iduci_ibRezervacija.getDrawable() == getDrawable(R.drawable.ic_playlist_add_check_black_48dp)){

                        iduci_ibRezervacija.setImageResource(R.drawable.ic_playlist_add_black_48dp);
                        Toast.makeText(getBaseContext(), "Registracija je otkazana", Toast.LENGTH_SHORT).show();
                    } else {

                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                        alertDialog.setTitle("Potvrda");
                        alertDialog.setMessage("Sigurni ste da zelite rezervirati ovaj let?");
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "DA",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getBaseContext(), "Uspijesno ste rezervirali let", Toast.LENGTH_SHORT).show();
                                        iduci_ibRezervacija.setImageResource(R.drawable.ic_playlist_add_check_black_48dp);
                                        iduci_ibRezervacija.setBackgroundColor(Color.RED);
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NE",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }
                else {
                    Toast.makeText(getBaseContext(), "Prvo se registrirajte", Toast.LENGTH_SHORT).show();
                }
        }
        });
    }


    public ArrayList<Flight> getTopPonuda() {
        ArrayList<Flight> flights = myDb.getAllFlights();

        Calendar trenutnoVrijeme = Calendar.getInstance();

        Iterator<Flight> i = flights.iterator();
        while (i.hasNext()) {
            Flight f = i.next();

            if (trenutnoVrijeme.after(f.getDateTime())) {
                i.remove();
            }
        }

        //Sortiranje
        Collections.sort(flights, FlightComparator.ComparatorFlightRezervacije());

        int velicina = flights.size();

        if (velicina > 10)
            velicina = 10;

        ArrayList<Flight> rezultat = new ArrayList<>(flights.subList(0,velicina));
        return rezultat;
    }

    private ArrayList<Flight> getIduciLetovi() {
        ArrayList<Flight> flights = myDb.getAllFlights();
        Calendar trenutnoVrijeme = Calendar.getInstance();
        Calendar zaSestMjeseci = Calendar.getInstance();
        zaSestMjeseci.add(Calendar.MONTH, 6);

        Iterator<Flight> i = flights.iterator();
        while (i.hasNext()) {
            Flight f = i.next();

            //Ako je prosao ili ako nije u iducih sest mjeseci
            if (trenutnoVrijeme.after(f.getDateTime()) || zaSestMjeseci.before(f.getDateTime())) {
                i.remove();
            }

            //
        }

        //Sortiranje
        Collections.sort(flights, FlightComparator.ComparatorFlightDatum());
        return flights;
    }



}