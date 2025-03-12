package org.schichtverwaltung.objectStructure;

import org.schichtverwaltung.zUtils.TimeStamps;

import java.time.LocalDate;
import java.util.ArrayList;

public class Day {

    private final int dayID;
    private final LocalDate day;

    private ArrayList<Service> services = new ArrayList<>();

    private TimeStamps timeStamps;

    public Day(int dayID, LocalDate day) {
        this.dayID = dayID;
        this.day = day;

        timeStamps = new TimeStamps();
    }

    public void addService (Service service) {
        services.add(service);
    }

    public void print() {
        System.out.println("\t\t\t" + day + " (" + dayID + " " + timeStamps.getTimeStampCreate() + " " + timeStamps.getTimeStampEdit());
        for (Service service : services) {
            service.print();
        }
    }
}
