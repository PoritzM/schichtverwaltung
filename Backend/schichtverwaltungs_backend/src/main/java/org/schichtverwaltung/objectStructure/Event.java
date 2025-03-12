package org.schichtverwaltung.objectStructure;

import org.schichtverwaltung.zUtils.TimeStamps;

import java.util.ArrayList;

public class Event {

    private final int eventID;
    private final String eventName;
    private final boolean showEvent;
    private final boolean registerOnEvent;

    private ArrayList<Day> days = new ArrayList<>();

    private TimeStamps timeStamps;

    public Event(int eventID, String eventName, boolean showEvent, boolean registerOnEvent) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.showEvent = showEvent;
        this.registerOnEvent = registerOnEvent;

        timeStamps = new TimeStamps();
    }

    public void addDay (Day day) {
        days.add(day);
    }
    public void print() {
        System.out.println("\t\t" + eventName + " (" + eventID + " | " + showEvent + " | " + registerOnEvent + " | " + timeStamps.getTimeStampCreate() + " | " + timeStamps.getTimeStampEdit() + ")");
        for (Day day: days) {
            day.print();
        }
    }
}
