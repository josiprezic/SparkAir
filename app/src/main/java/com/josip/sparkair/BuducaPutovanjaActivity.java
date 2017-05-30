package com.josip.sparkair;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.Calendar;

public class BuducaPutovanjaActivity extends AppCompatActivity {

    ListView lvBuducaPutovanja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buduca_putovanja);

        lvBuducaPutovanja = (ListView) findViewById(R.id.lvBuducaPutovanja);


        //Postavljanje custom adaptera za buduca putovanja
        final Flight[] futureFlights = new Flight[20];
        for (int i = 0; i < 20; i++) {
            futureFlights[i] = new Flight(1, 2, "Beč", Calendar.getInstance(), 400, true);
        }

        ListAdapter iduciLetoviAdapter1 = new CustomAdapterFlights(this, futureFlights);
        lvBuducaPutovanja.setAdapter(iduciLetoviAdapter1);
        lvBuducaPutovanja.setClickable(true);

        //Ono sto ce se desiti na klik itema sa liste
        lvBuducaPutovanja.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Flight flight = futureFlights[position];
            }
        });



    }
}
