package com.josip.sparkair;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Josip on 4.6.2017..
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //postavljanje trenutnog vremena
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //Kreiranje i vracanje nove istance TimePickerDialog-a
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        ((AddFlightActivity) getActivity()).izabranoVrijeme = true;
        ((AddFlightActivity) getActivity()).sat = hourOfDay;
        ((AddFlightActivity) getActivity()).minuta = minute;
        //Toast.makeText(getActivity().getApplicationContext(), Integer.toString(hourOfDay) + " sati", Toast.LENGTH_SHORT).show();
    }
}
