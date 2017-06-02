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

import java.util.Calendar;

/**
 * Created by Josip on 31.5.2017..
 */



public class MenuBuducaPutovanja extends Fragment{
    ListView lvBuducaPutovanjaa;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toast.makeText(getActivity().getApplicationContext(), "onViewCreated", Toast.LENGTH_SHORT);

        lvBuducaPutovanjaa = (ListView) getView().findViewById(R.id.lvBuducaPutovanjaa);

        //Postavljanje custom adaptera za buduca putovanja
        final Flight[] futureFlights = new Flight[20];
        for (int i = 0; i < 20; i++) {
            futureFlights[i] = new Flight(1, 2, "Beč", Calendar.getInstance(), 400, true);
        }

        ListAdapter iduciLetoviAdapter1 = new CustomAdapterFlights(getActivity().getApplicationContext(), futureFlights);
        lvBuducaPutovanjaa.setAdapter(iduciLetoviAdapter1);
        lvBuducaPutovanjaa.setClickable(true);

        //Ono sto ce se desiti na klik itema sa liste
        lvBuducaPutovanjaa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Flight flight = futureFlights[position];
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Toast.makeText(getActivity().getApplicationContext(), "onCreateView", Toast.LENGTH_SHORT);

        return inflater.inflate(R.layout.menu_buduca_putovanja, container, false);
    }
}
