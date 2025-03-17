package org.schichtverwaltung.objectStructure;

import org.schichtverwaltung.zUtils.TimeStamps;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.schichtverwaltung.dbTools.InsertMethods.insertDay;

public class Day {

    private int eventID;
    private int dayID;
    private LocalDate day;

    private ArrayList<Service> services = new ArrayList<>();

    private TimeStamps timeStamps;

//    public Day(int eventID, LocalDate day) {
//        this.eventID = eventID;
//        this.day = day;
//
//        timeStamps = new TimeStamps();
//    }


    public Day(int eventID, int dayID, LocalDate day, TimeStamps timeStamps) {
        this.eventID = eventID;
        this.dayID = dayID;
        this.day = day;
        this.timeStamps = timeStamps;
    }

    public int dayToDB () {
        dayID = insertDay(eventID, day, timeStamps.getTimeStampCreate(), timeStamps.getTimeStampEdit());
        return dayID;
    }

    public void print() {
        System.out.println("\t\t\t" + day + " (" + dayID + " | " + timeStamps.getTimeStampCreate() + " | " + timeStamps.getTimeStampEdit() + ")");
        for (Service service : services) {
            service.print();
        }
    }

    public void initDay (int eventID) {
        this.eventID = eventID;

        if (services == null) {
            services = new ArrayList<>();
        }
        timeStamps = new TimeStamps();
    }

    public void addService (Service service) {
        services.add(service);
    }

    public ArrayList<Service> getServices() {
        return services;
    }
}
