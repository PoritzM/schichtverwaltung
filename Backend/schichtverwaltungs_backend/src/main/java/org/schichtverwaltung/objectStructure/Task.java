package org.schichtverwaltung.objectStructure;

import org.schichtverwaltung.dbTools.InfoSet;
import org.schichtverwaltung.exceptions.BackendException;
import org.schichtverwaltung.exceptions.ItemNotFoundException;
import org.schichtverwaltung.zUtils.TimeStamps;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import static org.schichtverwaltung.dbTools.InsertMethods.insertTask;
import static org.schichtverwaltung.dbTools.SelectMethods.selectTable;
import static org.schichtverwaltung.dbTools.UpdateMethods.updateTable;

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

    public void updateTaskOnDB () throws SQLException, ItemNotFoundException, BackendException {
        InfoSet infoSet = selectTable("taskID", String.valueOf(taskID), "tasks");

        if(infoSet.getColumnValues("taskID").isEmpty()) {
            throw new ItemNotFoundException("TaskID " + taskID + " not found!");
        }

        if((Integer) infoSet.getColumnValues("taskID").get(0) != taskID ||
                (Integer) infoSet.getColumnValues("serviceID").get(0) != serviceID ||
                (Integer) infoSet.getColumnValues("dayID").get(0) != dayID ||
                (Integer) infoSet.getColumnValues("eventID").get(0) != eventID) {
            throw new BackendException("Missmatch in a ID (Task Object vs Task in DB)");
        }

        ArrayList<Object> taskDescriptions = infoSet.getColumnValues("taskDescription");
        ArrayList<Object> neededWorkers = infoSet.getColumnValues("neededWorker");

        if (!taskDescriptions.get(0).equals(taskDescription)) {
            updateTable("tasks", "taskDescription", taskDescription, "taskID", String.valueOf(taskID));
        }

        if (!neededWorkers.get(0).equals(neededWorker)) {
            updateTable("tasks", "neededWorker", String.valueOf(neededWorker), "taskID", String.valueOf(taskID));
        }
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

    public int getTaskID() {
        return taskID;
    }

    public int getDayID() {
        return dayID;
    }

    public int getServiceID() {
        return serviceID;
    }
}
