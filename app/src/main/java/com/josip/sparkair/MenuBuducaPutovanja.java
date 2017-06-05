package com.josip.sparkair;

import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.josip.sparkair.Util.FlightComparator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by Josip on 31.5.2017..
 */



public class MenuBuducaPutovanja extends Fragment{
    ListView lvBuducaPutovanjaa;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvBuducaPutovanjaa = (ListView) getView().findViewById(R.id.lvBuducaPutovanjaa);

        //Postavljanje custom adaptera za buduca putovanja
        final ArrayList<Flight> futureFlights = getBuducaPutovanja();

        ListAdapter iduciLetoviAdapter1 = new CustomAdapterFlights(getActivity().getApplicationContext(), futureFlights);
        lvBuducaPutovanjaa.setAdapter(iduciLetoviAdapter1);
        lvBuducaPutovanjaa.setClickable(true);

        //Ono sto ce se desiti na klik itema sa liste
        lvBuducaPutovanjaa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Flight flight = futureFlights.get(position);
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu_buduca_putovanja, container, false);
    }


    private ArrayList<Flight> getBuducaPutovanja() {
        DatabaseHelper myDb = new DatabaseHelper(getActivity().getApplicationContext());
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
        }

        //Sortiranje
        Collections.sort(flights, FlightComparator.ComparatorFlightDatum());
        return flights;
    }
}
