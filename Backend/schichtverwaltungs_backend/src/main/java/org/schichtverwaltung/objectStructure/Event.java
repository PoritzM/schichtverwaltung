package org.schichtverwaltung.objectStructure;

import org.schichtverwaltung.zUtils.TimeStamps;

import java.util.ArrayList;

import static org.schichtverwaltung.dbTools.InsertMethods.insertEvent;

public class Event {

    private int eventID;
    private String eventName;
    private boolean showEvent;
    private boolean registerOnEvent;

    private ArrayList<Day> days = new ArrayList<>();

    private TimeStamps timeStamps;

//    public Event(String eventName, boolean showEvent, boolean registerOnEvent) {
//        this.eventName = eventName;
//        this.showEvent = showEvent;
//        this.registerOnEvent = registerOnEvent;
//
//        timeStamps = new TimeStamps();
//    }

    public int eventToDB () {
        eventID = insertEvent(eventName, showEvent, registerOnEvent, timeStamps.getTimeStampCreate(), timeStamps.getTimeStampEdit());
        return eventID;
    }

    public void print() {
        System.out.println("\t\t" + eventName + " (" + eventID + " | " + showEvent + " | " + registerOnEvent + " | " + timeStamps.getTimeStampCreate() + " | " + timeStamps.getTimeStampEdit() + ")");
        for (Day day: days) {
            day.print();
        }
    }

    public void initEvent () {
        if (days == null) {
            days = new ArrayList<>();
        }
        timeStamps = new TimeStamps();
    }

    public ArrayList<Day> getDays() {
        return days;
    }
}
