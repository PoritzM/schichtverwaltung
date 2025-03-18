package org.schichtverwaltung.objectStructure;

import org.schichtverwaltung.zUtils.GetTimeStamp;
import org.schichtverwaltung.zUtils.TimeStamps;

import java.util.Date;

import static org.schichtverwaltung.dbTools.InsertMethods.insertWorker;
import static org.schichtverwaltung.zUtils.GetTimeStamp.getTimeStamp;

public class Worker {

    private int eventID;
    private int dayID;
    private int serviceID;
    private int taskID;
    private int workerID;
    private String workerName;

    private TimeStamps timeStamps;

//    public Worker(int eventID, int dayID, int serviceID, int taskID, String workerName) {
//        this.eventID = eventID;
//        this.dayID = dayID;
//        this.serviceID = serviceID;
//        this.taskID = taskID;
//        this.workerName = workerName;
//
//        timeStamps = new TimeStamps();
//    }


    public Worker(int eventID, int dayID, int serviceID, int taskID, int workerID, String workerName, TimeStamps timeStamps) {
        this.eventID = eventID;
        this.dayID = dayID;
        this.serviceID = serviceID;
        this.taskID = taskID;
        this.workerID = workerID;
        this.workerName = workerName;
        this.timeStamps = timeStamps;
    }

    public int workerToDB () {
        workerID = insertWorker(eventID, dayID, serviceID, taskID, workerName, timeStamps.getTimeStampCreate(), timeStamps.getTimeStampEdit());
        return workerID;
    }

    public void print() {
        System.out.println("\t\t\t\t\t\t" + workerName + " (" + workerID + " " + timeStamps.getTimeStampCreate() + " " + timeStamps.getTimeStampEdit() + ")");
    }

    public void initWorker (int eventID, int dayID, int serviceID, int taskID) {
        this.eventID = eventID;
        this.dayID = dayID;
        this.serviceID = serviceID;
        this.taskID = taskID;

        timeStamps = new TimeStamps();
    }

    public int getEventID() {
        return eventID;
    }

    public int getDayID() {
        return dayID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public int getTaskID() {
        return taskID;
    }

    public int getWorkerID() {
        return workerID;
    }

    public String getWorkerName() {
        return workerName;
    }
}
