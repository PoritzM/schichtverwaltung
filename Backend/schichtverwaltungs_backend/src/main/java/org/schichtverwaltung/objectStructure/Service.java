package org.schichtverwaltung.objectStructure;

import org.schichtverwaltung.zUtils.TimeStamps;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import static org.schichtverwaltung.dbTools.InsertMethods.insertService;

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
}
