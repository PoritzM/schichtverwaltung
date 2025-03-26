package org.schichtverwaltung.objectStructure;

import org.schichtverwaltung.dbTools.InfoSet;
import org.schichtverwaltung.exceptions.BackendException;
import org.schichtverwaltung.exceptions.ItemNotFoundException;
import org.schichtverwaltung.zUtils.TimeStamps;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.schichtverwaltung.dbTools.InsertMethods.insertDay;
import static org.schichtverwaltung.dbTools.SelectMethods.selectTable;
import static org.schichtverwaltung.dbTools.UpdateMethods.updateTable;

public class Day {

    private int eventID;
    private int dayID;
    private LocalDate day;

    private ArrayList<Service> services = new ArrayList<>();

    private TimeStamps timeStamps;

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

    public void updateDayOnDB () throws SQLException, ItemNotFoundException, BackendException {

        InfoSet infoSet = selectTable("dayID", String.valueOf(dayID), "days");

        if(infoSet.getColumnValues("dayID").isEmpty()) {
            throw new ItemNotFoundException("dayID " + dayID + " not found!");
        }

        if((Integer) infoSet.getColumnValues("dayID").get(0) != dayID ||
                (Integer) infoSet.getColumnValues("eventID").get(0) != eventID) {
            throw new BackendException("Missmatch in a ID (Day Object vs Day in DB)");
        }

        ArrayList<Object> days = infoSet.getColumnValues("day");

        if (!days.get(0).equals(day)) {
            updateTable("days", "day", String.valueOf(day), "dayID", String.valueOf(dayID));
        }
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

    public int getDayID() {
        return dayID;
    }
}
