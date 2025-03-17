package org.schichtverwaltung.functions;

import org.schichtverwaltung.dbTools.InfoSet;
import org.schichtverwaltung.objectStructure.Day;
import org.schichtverwaltung.objectStructure.Event;
import org.schichtverwaltung.zUtils.TimeStamps;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.schichtverwaltung.dbTools.SelectMethods.selectTable;
import static org.schichtverwaltung.dbTools.SelectMethods.selectTableAll;
import static org.schichtverwaltung.zUtils.StringToDateParser.parseDateString;
import static org.schichtverwaltung.zUtils.StringToLocalDate.parseLocalDateString;

public class SelectShift {

    public static Event selectShift (int eventID) throws SQLException, ParseException {

        Event event = selectEvent(eventID);

        InfoSet infoSetDays = selectTable("eventID", String.valueOf(eventID), "days");
        ArrayList<Object> dayIDs = infoSetDays.getColumnValues("dayID");

        int dayIndex = 0;
        for (Object dayID : dayIDs) {
            event.addDay(selectDay((Integer) dayID));

            InfoSet infoSetTasks = selectTable("dayID", String.valueOf(dayID), "services");
            ArrayList<Object> serviceIDs = infoSetTasks.getColumnValues("serviceID");

            for (Object services : serviceIDs) {
                event.getDays().get(dayIndex).addService(se); //todo SelectService hinzufügen - An den index denken

            }
            dayIndex++;
        }

        return event;

    }

    public static void selectAllShifts () throws SQLException, ParseException {

        InfoSet infoSetEvent = selectTableAll("events");

        ArrayList<Object> eventIDs = infoSetEvent.getColumnValues("eventID");
        ArrayList<Event> events = new ArrayList<>();

        for (Object eventID : eventIDs) {
            events.add(selectShift((Integer) eventID));
        }

        for (Event event : events) {
            event.print();
        }
    }

    private static Event selectEvent (int eventID) throws SQLException, ParseException {
        InfoSet infoSetEvent = selectTable("eventID",String.valueOf(eventID), "events");

        ArrayList<Object> eventNames = infoSetEvent.getColumnValues("eventName");
        ArrayList<Object> showEvents = infoSetEvent.getColumnValues("showEvent");
        ArrayList<Object> registerOnEvents = infoSetEvent.getColumnValues("registerOnEvent");
        ArrayList<Object> timeStampsCreate = infoSetEvent.getColumnValues("timeStampCreate");
        ArrayList<Object> timeStampsEdit = infoSetEvent.getColumnValues("timeStampEdit");

        return new Event(eventID,(String) eventNames.getFirst(), Boolean.parseBoolean((String) showEvents.getFirst()), Boolean.parseBoolean((String) registerOnEvents.getFirst()), new TimeStamps(parseDateString((String) timeStampsCreate.getFirst()),parseDateString((String) timeStampsEdit.getFirst())));
    }

    private static Day selectDay (int dayID) throws SQLException, ParseException {
        InfoSet infoSetEvent = selectTable("dayID",String.valueOf(dayID), "days");

        ArrayList<Object> eventIDs = infoSetEvent.getColumnValues("eventID");
        ArrayList<Object> days = infoSetEvent.getColumnValues("day");
        ArrayList<Object> timeStampsCreate = infoSetEvent.getColumnValues("timeStampCreate");
        ArrayList<Object> timeStampsEdit = infoSetEvent.getColumnValues("timeStampEdit");

        return new Day((Integer) eventIDs.getFirst(),dayID, parseLocalDateString((String) days.getFirst()), new TimeStamps(parseDateString((String) timeStampsCreate.getFirst()),parseDateString((String) timeStampsEdit.getFirst())));
    }
}
