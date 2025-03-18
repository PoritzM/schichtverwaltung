package org.schichtverwaltung.objectStructure;

import org.schichtverwaltung.zUtils.TimeStamps;

import java.util.ArrayList;
import java.util.Date;

import static org.schichtverwaltung.dbTools.InsertMethods.insertTask;

public class Task {

    private int eventID;
    private int dayID;
    private int serviceID;
    private int taskID;
    private String taskDescription;
    private int neededWorker;

    private ArrayList<Worker> workers = new ArrayList<>();

    private TimeStamps timeStamps;

//    public Task(int eventID, int dayID, int serviceID, String taskDescription, int neededWorker) {
//        this.eventID = eventID;
//        this.dayID = dayID;
//        this.serviceID = serviceID;
//        this.taskDescription = taskDescription;
//        this.neededWorker = neededWorker;
//
//        timeStamps = new TimeStamps();
//    }


    public Task(int eventID, int dayID, int serviceID, int taskID, String taskDescription, int neededWorker, TimeStamps timeStamps) {
        this.eventID = eventID;
        this.dayID = dayID;
        this.serviceID = serviceID;
        this.taskID = taskID;
        this.taskDescription = taskDescription;
        this.neededWorker = neededWorker;
        this.timeStamps = timeStamps;
    }

    public int taskToDB () {
        taskID = insertTask(eventID, dayID, serviceID, taskDescription, neededWorker, timeStamps.getTimeStampCreate(), timeStamps.getTimeStampEdit());
        return taskID;
    }

    public void addWorker (Worker worker) {
        workers.add(worker);
    }

    public void print() {
        System.out.println("\t\t\t\t\t" + taskDescription + " (" + taskID + " | " + timeStamps.getTimeStampCreate() + " | " + timeStamps.getTimeStampEdit() + ")");
        for (Worker worker : workers) {
            worker.print();
        }
    }

    public void initTask (int eventID, int dayID, int serviceID) {

        this.eventID = eventID;
        this.dayID = dayID;
        this.serviceID = serviceID;

        if (workers == null) {
            workers = new ArrayList<>();
        }
        timeStamps = new TimeStamps();
    }

    public ArrayList<Worker> getWorkers() {
        return workers;
    }
}
