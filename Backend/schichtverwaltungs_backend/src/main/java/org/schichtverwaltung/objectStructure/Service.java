package org.schichtverwaltung.objectStructure;

import org.schichtverwaltung.dbTools.InfoSet;
import org.schichtverwaltung.exceptions.BackendException;
import org.schichtverwaltung.exceptions.ItemNotFoundException;
import org.schichtverwaltung.zUtils.TimeStamps;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import static org.schichtverwaltung.dbTools.InsertMethods.insertService;
import static org.schichtverwaltung.dbTools.SelectMethods.selectTable;
import static org.schichtverwaltung.dbTools.UpdateMethods.updateTable;

public class Service {

    private int eventID;
    private int dayID;
    private int serviceID;
    private String serviceDescription;
    private LocalTime timeStart;
    private LocalTime timeEnd;

    private ArrayList<Task> tasks = new ArrayList<>();

    private TimeStamps timeStamps;

//    public Service(int eventID, int dayID, String serviceDescription, LocalTime timeStart, LocalTime timeEnd) {
//        this.eventID = eventID;
//        this.dayID = dayID;
//        this.serviceDescription = serviceDescription;
//        this.timeStart = timeStart;
//        this.timeEnd = timeEnd;
//
//        timeStamps = new TimeStamps();
//    }

    public Service(int eventID, int dayID, int serviceID, String serviceDescription, LocalTime timeStart, LocalTime timeEnd, TimeStamps timeStamps) {
        this.eventID = eventID;
        this.dayID = dayID;
        this.serviceID = serviceID;
        this.serviceDescription = serviceDescription;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.timeStamps = timeStamps;
    }

    public int serviceToDB () {
        serviceID = insertService(eventID, dayID, serviceDescription, timeStamps.getTimeStampCreate(), timeStamps.getTimeStampEdit(), timeStart, timeEnd);
        return serviceID;
    }

    public void updateServiceOnDB () throws SQLException, ItemNotFoundException, BackendException {
        InfoSet infoSet = selectTable("serviceID", String.valueOf(serviceID), "services");

        if(infoSet.getColumnValues("serviceID").isEmpty()) {
            throw new ItemNotFoundException("ServiceID " + serviceID + " not found!");
        }

        if((Integer) infoSet.getColumnValues("serviceID").get(0) != serviceID ||
                (Integer) infoSet.getColumnValues("dayID").get(0) != dayID ||
                (Integer) infoSet.getColumnValues("eventID").get(0) != eventID) {
            throw new BackendException("Missmatch in a ID (Service Object vs Service in DB)");
        }

        ArrayList<Object> serviceDescriptions = infoSet.getColumnValues("serviceDescription");
        ArrayList<Object> timeStarts = infoSet.getColumnValues("timeStart");
        ArrayList<Object> timeEnds = infoSet.getColumnValues("timeEnd");

        if (!serviceDescriptions.get(0).equals(serviceDescription)) {
            updateTable("services", "serviceDescription", serviceDescription, "serviceID", String.valueOf(serviceID));
        }

        if (!timeStarts.get(0).equals(timeStart)) {
            updateTable("services", "timeStart", String.valueOf(timeStart), "serviceID", String.valueOf(serviceID));
        }

        if (!timeEnds.get(0).equals(timeEnd)) {
            updateTable("services", "timeEnd", String.valueOf(timeEnd), "serviceID", String.valueOf(serviceID));
        }
    }

    public void print() {
        System.out.println("\t\t\t\t" + serviceDescription + " (" + serviceID + " | " + timeStamps.getTimeStampCreate() + " | " + timeStamps.getTimeStampEdit() + ")");
        for (Task task : tasks) {
            task.print();
        }
    }

    public void initService (int eventID, int dayID) {
        this.eventID = eventID;
        this.dayID = dayID;

        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        timeStamps = new TimeStamps();
    }

    public void addTask (Task task) {
        tasks.add(task);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int getServiceID() {
        return serviceID;
    }
}
