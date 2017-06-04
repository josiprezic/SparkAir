package com.josip.sparkair;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.josip.sparkair.Util.Util;

import java.util.Calendar;

public class AddFlightActivity extends AppCompatActivity {
    EditText addFlight_etDestinacija;
    EditText addFlight_etCijena;
    Button addFlight_btOdaberiDatum;
    Button addFlight_btOdaberiVrijeme;
    CheckBox addFlight_cbActive;
    Button addFlight_btSpremiLet;
    boolean izabranoVrijeme;
    boolean izaabranDatum;
    public int minuta, sat, dan, mjesec, godina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);

        //Referenciranje
        addFlight_etDestinacija = (EditText) findViewById(R.id.addFlight_etDestinacija);
        addFlight_etCijena = (EditText) findViewById(R.id.addFlight_etCijena);
        addFlight_btOdaberiDatum = (Button) findViewById(R.id.addFlight_btOdaberiDatum);
        addFlight_btOdaberiVrijeme = (Button) findViewById(R.id.addFlight_btOdaberiVrijeme);
        addFlight_cbActive = (CheckBox) findViewById(R.id.addFlight_cbActive);
        addFlight_btSpremiLet = (Button) findViewById(R.id.addFlight_btSpremiLet);
        izaabranDatum = false;
        izabranoVrijeme = false;


        //Time picker dijalog
        addFlight_btOdaberiVrijeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Otvaranje TimePickera
                DialogFragment vrijemeDijalog = new TimePickerFragment();
                vrijemeDijalog.show(getSupportFragmentManager(), "timePicker");

            }
        });

        //Date picker dijalog
        addFlight_btOdaberiDatum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datumDijalog = new DatePickerFragment();
                datumDijalog.show(getSupportFragmentManager(), "datePicker");

            }
        });


        addFlight_btSpremiLet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  public Flight(int flightID, int userID, String destination, Calendar dateTime, double price, boolean active)
                if (Util.isEmpty(addFlight_etDestinacija)) {
                    Toast.makeText(AddFlightActivity.this, "Unesite naziv destinacije!", Toast.LENGTH_SHORT).show();
                    addFlight_etDestinacija.requestFocus();
                }
                else if(Util.isEmpty(addFlight_etCijena)) {
                    Toast.makeText(AddFlightActivity.this, "Unesite cijenu putovanja!", Toast.LENGTH_SHORT).show();
                    addFlight_etCijena.requestFocus();
                }
                else if (izaabranDatum == false) { //ovome se pristupa preko fragmenta za datum
                    Toast.makeText(AddFlightActivity.this, "Unesite datum polaska!", Toast.LENGTH_SHORT).show();
                }
                else if(izabranoVrijeme == false) { // ovome se pristupa preko fragmenta za vrijeme
                    Toast.makeText(AddFlightActivity.this, "Unesite vrijeme polaska!", Toast.LENGTH_SHORT).show();
                }
                else {
                    User user = Util.getCurrentUser(getApplicationContext());
                    Calendar c = Calendar.getInstance();
                    c.set(godina, mjesec, dan, sat, minuta);
                    c.get(Calendar.WEEK_OF_YEAR); // potrebno radi preracunavanja


                    //Ako je sve u redu spremamo novi flight u bazu
                    DatabaseHelper myDb = new DatabaseHelper(getApplicationContext());
                    boolean uspjesno = myDb.insertFlight(user.getUserID(), addFlight_etDestinacija.getText().toString(), c, Double.valueOf(addFlight_etCijena.getText().toString()), addFlight_cbActive.isChecked());
                    if (uspjesno) {
                        Toast.makeText(AddFlightActivity.this, "Uspješno ste izvšili unos novog leta!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddFlightActivity.this, "Došlo je do greške...", Toast.LENGTH_SHORT).show();
                    }
                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        });


    }
}
