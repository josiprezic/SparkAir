package com.josip.sparkair;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
//import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Josip on 4.6.2017..
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //postavljanje trenutnog datuma
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        //Kreiranje i vracanje nove istance TimePickerDialog-a
        return new DatePickerDialog(getActivity(), this, year, month, day);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        //spremanje datuma u varijable AddFlightActivity-ja

        AddFlightActivity a =  (AddFlightActivity) getActivity();
        a.izaabranDatum = true;
        a.dan = dayOfMonth;
        a.mjesec = month;
        a.godina = year;

        a.addFlight_etDatum.setText(Integer.toString(dayOfMonth) + "." +Integer.toString(month + 1) + "." +Integer.toString(year) + ".");
    }
}
