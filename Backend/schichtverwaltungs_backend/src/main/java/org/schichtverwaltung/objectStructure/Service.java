package org.schichtverwaltung.objectStructure;

import org.schichtverwaltung.zUtils.TimeStamps;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Service {

    private final int serviceID;
    private final String serviceDescription;
    private final LocalTime timeStart;
    private final LocalTime timeEnd;

    private ArrayList<Task> tasks = new ArrayList<>();

    private TimeStamps timeStamps;

    public Service(int serviceID, String serviceDescription, LocalTime timeStart, LocalTime timeEnd) {
        this.serviceID = serviceID;
        this.serviceDescription = serviceDescription;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;

        timeStamps = new TimeStamps();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void print() {
        System.out.println("\t\t\t\t" + serviceDescription + " (" + serviceID + " " + timeStamps.getTimeStampCreate() + " " + timeStamps.getTimeStampEdit());
        for (Task task : tasks) {
            task.print();
        }
    }
}
