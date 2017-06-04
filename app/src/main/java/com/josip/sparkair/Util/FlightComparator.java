package com.josip.sparkair.Util;

import com.josip.sparkair.Flight;

import java.util.Comparator;

/**
 * Created by Josip on 4.6.2017..
 */

public class FlightComparator{

    public static Comparator<Flight> ComparatorFlightRezervacije() {
        return new Comparator<Flight>() {
            @Override
            public int compare(Flight o1, Flight o2) {
                int f1 = o1.getBrojRezervacija();
                int f2 = o2.getBrojRezervacija();

                if (f1 < f2) {
                    return 1;
                }
                else if (f1 > f2) {
                    return -1;
                }
                else if(o1.getDateTime().before(o2.getDateTime())){
                    return -1;
                }
                else if (o1.getDateTime().after(o2.getDateTime())) {
                    return 1;
                }
                return 0;
            }
        };
    }

    public static Comparator<Flight> ComparatorFlightDatum() {
        return new Comparator<Flight>() {

            @Override
            public int compare(Flight o1, Flight o2) {
                if(o1.getDateTime().before(o2.getDateTime())){
                    return -1;
                }
                else if (o1.getDateTime().after(o2.getDateTime())) {
                    return 1;
                }
                return 0;
            }
        };
    }

}
