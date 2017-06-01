package com.josip.sparkair;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Josip on 30.5.2017..
 */

class CustomAdapterFlights extends ArrayAdapter<Flight> {

    public CustomAdapterFlights(Context context, Flight[] flights) {
        super(context,R.layout.custom_row_flights, flights);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater flightInflater = LayoutInflater.from(getContext());
        View customView = flightInflater.inflate(R.layout.custom_row_flights, parent, false);

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

        //Iz nekog razloga su sva slova postala bijela pa ih mijenjam u crna
        tvOdrediste.setTextColor(Color.BLACK);
        tvDatum.setTextColor(Color.BLACK);
        tvVrijeme.setTextColor(Color.BLACK);
        tvCijena.setTextColor(Color.BLACK);
        tvBrojRezervacija.setTextColor(Color.BLACK);

        ivSlika.setImageResource(R.drawable.logo_on_transparent);
        tvOdrediste.setText(singleFlightItem.getDestination());
        tvDatum.setText(singleFlightItem.getDateString());
        tvVrijeme.setText(singleFlightItem.getTimeString());
        tvCijena.setText(Double.toString(singleFlightItem.getPrice()) + "KM");
        tvBrojRezervacija.setText(Integer.toString(4));

        //Iz nekog razloga su sva slova postala bijela pa ih mijenjam u crna
        tvOdrediste.setTextColor(Color.BLACK);
        tvDatum.setTextColor(Color.BLACK);
        tvVrijeme.setTextColor(Color.BLACK);
        tvCijena.setTextColor(Color.BLACK);
        tvBrojRezervacija.setTextColor(Color.BLACK);

        return customView;
    }
}
