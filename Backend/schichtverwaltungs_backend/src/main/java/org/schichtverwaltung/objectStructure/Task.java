package org.schichtverwaltung.objectStructure;

import org.schichtverwaltung.zUtils.TimeStamps;

import java.util.ArrayList;
import java.util.Date;

public class Task {

    private final int taskID;
    private final String taskDescription;

    private ArrayList<Worker> workers = new ArrayList<>();

    private TimeStamps timeStamps;

    public Task(int taskID, String taskDescription) {
        this.taskID = taskID;
        this.taskDescription = taskDescription;

        timeStamps = new TimeStamps();
    }

    public void addWorker(Worker worker) {
        workers.add(worker);
    }

    public void print() {
        System.out.println("\t\t\t\t\t" + taskDescription + " (" + taskID + " " + timeStamps.getTimeStampCreate() + " " + timeStamps.getTimeStampEdit());
        for (Worker worker : workers) {
            worker.print();
        }
    }
}
