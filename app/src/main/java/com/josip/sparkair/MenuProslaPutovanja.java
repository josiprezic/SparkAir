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

import java.util.Calendar;

/**
 * Created by Josip on 3.6.2017..
 */

public class MenuProslaPutovanja extends Fragment{
    public ListView lvProslaPutovanjaa;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Copy pocetak

        lvProslaPutovanjaa = (ListView) getView().findViewById(R.id.lvProslaPutovanjaa);

        //Postavljanje custom adaptera za prosla putovanja
        final Flight[] pastFlights = new Flight[20];
        for (int i = 0; i < 20; i++) {
            pastFlights[i] = new Flight(1, 2, "Prag", Calendar.getInstance(), 400, true);
        }

        ListAdapter iduciLetoviAdapter1 = new CustomAdapterFlights(getActivity().getApplicationContext(), pastFlights);
        lvProslaPutovanjaa.setAdapter(iduciLetoviAdapter1);
        lvProslaPutovanjaa.setClickable(true);

        //Ono sto ce se desiti na klik itema sa liste
        lvProslaPutovanjaa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Flight flight = pastFlights[position];
            }
        });

        //Copy kraj

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu_prosla_putovanja, container, false);
    }
}
