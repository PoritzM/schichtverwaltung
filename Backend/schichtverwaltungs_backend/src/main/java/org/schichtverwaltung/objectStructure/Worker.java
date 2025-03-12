package org.schichtverwaltung.objectStructure;

import org.schichtverwaltung.zUtils.GetTimeStamp;
import org.schichtverwaltung.zUtils.TimeStamps;

import java.util.Date;

import static org.schichtverwaltung.zUtils.GetTimeStamp.getTimeStamp;

public class Worker {

    private final int workerID;
    private final String workerName;

    private TimeStamps timeStamps;

    public Worker(int workerID, String workerName) {
        this.workerID = workerID;
        this.workerName = workerName;

        timeStamps = new TimeStamps();
    }

    public void print() {
        System.out.println("\t\t\t\t\t\t" + workerName + " (" + workerID + " " + timeStamps.getTimeStampCreate() + " " + timeStamps.getTimeStampEdit() + ")");
    }
}
