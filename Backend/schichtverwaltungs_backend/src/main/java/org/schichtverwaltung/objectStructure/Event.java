package org.schichtverwaltung.objectStructure;

import org.schichtverwaltung.dbTools.InfoSet;
import org.schichtverwaltung.exceptions.ItemNotFoundException;
import org.schichtverwaltung.zUtils.ReturnInfos;
import org.schichtverwaltung.zUtils.TimeStamps;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.schichtverwaltung.dbTools.InsertMethods.insertEvent;
import static org.schichtverwaltung.dbTools.SelectMethods.selectTable;
import static org.schichtverwaltung.dbTools.UpdateMethods.updateTable;

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
//    }

    public Event(int eventID,String eventName, boolean showEvent, boolean registerOnEvent, TimeStamps timeStamps) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.showEvent = showEvent;
        this.registerOnEvent = registerOnEvent;
        this.timeStamps = timeStamps;
    }

    public int eventToDB () {
        eventID = insertEvent(eventName, showEvent, registerOnEvent, timeStamps.getTimeStampCreate(), timeStamps.getTimeStampEdit());
        return eventID;
    }

    public void updateEventOnDB () throws SQLException, ItemNotFoundException {
        InfoSet infoSet = selectTable("eventID", String.valueOf(eventID), "events");

        if(infoSet.getColumnValues("eventID").isEmpty()) {
            throw new ItemNotFoundException("EventID " + eventID + " not found!");
        }

        ArrayList<Object> eventsName = infoSet.getColumnValues("eventName");
        ArrayList<Object> showEvents = infoSet.getColumnValues("showEvent");
        ArrayList<Object> registerOnEvents = infoSet.getColumnValues("registerOnEvent");

        if (!eventsName.get(0).equals(eventName)) {
            updateTable("events", "eventName", eventName, "eventID", String.valueOf(eventID));
        }

        if (!showEvents.get(0).equals(showEvent)) {
            updateTable("events", "showEvent", String.valueOf(showEvent), "eventID", String.valueOf(eventID));
        }

        if (!registerOnEvents.get(0).equals(registerOnEvent)) {
            updateTable("events", "registerOnEvent", String.valueOf(registerOnEvent), "eventID", String.valueOf(eventID));
        }
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

    public void initEvent () {
        if (days == null) {
            days = new ArrayList<>();
        }
        timeStamps = new TimeStamps();
    }

    public ArrayList<Day> getDays() {
        return days;
    }

    public int getEventID() {
        return eventID;
    }
}
