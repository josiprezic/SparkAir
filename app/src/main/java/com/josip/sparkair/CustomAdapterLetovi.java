package com.josip.sparkair;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;


/**
 * Created by Josip on 29.5.2017..
 */

 class CustomAdapterIduciLetovi extends ArrayAdapter<Flight> {

    public CustomAdapterIduciLetovi(Context context, Flight[] flights) {
        super(context,R.layout.custom_row_iduci_letovi, flights);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater flightInflater = LayoutInflater.from(getContext());
        View customView = flightInflater.inflate(R.layout.custom_row_iduci_letovi, parent, false);

        //Get references
        Flight singleFlightItem = getItem(position);

        ImageView ivSlika = (ImageView) customView.findViewById(R.id.iduci_ivLogo);
        TextView tvOdrediste = (TextView) customView.findViewById(R.id.iduci_tvOdrediste);
        TextView tvDatum = (TextView) customView.findViewById(R.id.iduci_tvDatum);
        TextView tvVrijeme = (TextView) customView.findViewById(R.id.iduci_tvVrijeme);
        TextView tvCijena = (TextView) customView.findViewById(R.id.iduci_tvCijena);
        ImageButton ibBiljeska = (ImageButton) customView.findViewById(R.id.iduci_ibBiljeska);
        ImageButton ibRezerviraj = (ImageButton) customView.findViewById(R.id.iduci_ibRezervacija);
        TextView tvBrojRezervacija  = (TextView) customView.findViewById(R.id.iduci_tvBrojRezervacija);


        ivSlika.setImageResource(R.drawable.logo2);
        tvOdrediste.setText(singleFlightItem.getDestination());
        tvDatum.setText(singleFlightItem.getDateString());
        tvVrijeme.setText(singleFlightItem.getTimeString());
        tvCijena.setText(Double.toString(singleFlightItem.getPrice()) + "KM");


        tvBrojRezervacija.setText(Integer.toString(4));

        return customView;
    }
}
