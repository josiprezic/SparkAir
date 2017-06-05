package com.josip.sparkair;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.josip.sparkair.Util.FlightComparator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by Josip on 3.6.2017..
 */

public class MenuProslaPutovanja extends Fragment {
    public ListView lvProslaPutovanjaa;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvProslaPutovanjaa = (ListView) getView().findViewById(R.id.lvProslaPutovanjaa);

        //Postavljanje custom adaptera za prosla putovanja
        final ArrayList<Flight> pastFlights =getPosljednjaPutovanja();


        ListAdapter iduciLetoviAdapter1 = new CustomAdapterFlights(getActivity().getApplicationContext(), pastFlights);
        lvProslaPutovanjaa.setAdapter(iduciLetoviAdapter1);
        lvProslaPutovanjaa.setClickable(true);

        //Ono sto ce se desiti na klik itema sa liste
        lvProslaPutovanjaa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Flight flight = pastFlights.get(position);
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu_prosla_putovanja, container, false);
    }

    private ArrayList<Flight> getPosljednjaPutovanja() {
        DatabaseHelper myDb = new DatabaseHelper(getActivity().getApplicationContext());
        ArrayList<Flight> flights = myDb.getAllFlights();
        Calendar trenutnoVrijeme = Calendar.getInstance();
        Calendar prijeTriMjeseca = Calendar.getInstance();
        prijeTriMjeseca.add(Calendar.MONTH, -3);

        Iterator<Flight> i = flights.iterator();
        while (i.hasNext()) {
            Flight f = i.next();

            //Ako jos nije bio ili ako nije u posljednja tri mjesecaa
            if (trenutnoVrijeme.before(f.getDateTime()) || prijeTriMjeseca.after(f.getDateTime())) {
                i.remove();
            }
        }
        //Sortiranje
        Collections.sort(flights, FlightComparator.ComparatorFlightDatum());
        Collections.reverse(flights);
        return flights;
    }
}
