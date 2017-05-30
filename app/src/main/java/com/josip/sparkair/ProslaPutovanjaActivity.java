package com.josip.sparkair;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.Calendar;

public class ProslaPutovanjaActivity extends AppCompatActivity {

    ListView lvProslaPutovanja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prosla_putovanja);

        lvProslaPutovanja = (ListView) findViewById(R.id.lvProslaPutovanja);

        //Postavljanje custom adaptera za TopPonuda letove
        final Flight[] pastFlights = new Flight[20];
        for (int i = 0; i < 20; i++) {
            pastFlights[i] = new Flight(1, 2, "Prag", Calendar.getInstance(), 400, true);
        }

        ListAdapter iduciLetoviAdapter1 = new CustomAdapterIduciLetovi(this, pastFlights);
        lvProslaPutovanja.setAdapter(iduciLetoviAdapter1);
        lvProslaPutovanja.setClickable(true);

        //Ono sto ce se desiti na klik itema sa liste
        lvProslaPutovanja.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Flight flight = pastFlights[position];
            }
        });
    }
}
