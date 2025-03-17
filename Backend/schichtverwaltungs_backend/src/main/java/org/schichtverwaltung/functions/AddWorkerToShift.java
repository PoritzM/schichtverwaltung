package org.schichtverwaltung.functions;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.schichtverwaltung.dbTools.InfoSet;
import org.schichtverwaltung.objectStructure.Worker;

import java.io.IOError;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.schichtverwaltung.dbTools.SelectMethods.*;

public class AddWorkerToShift {

    public static void addWorkerToShift (String jsonString) throws SQLException, IOException {

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        Worker worker = gson.fromJson(jsonObject.getAsJsonObject("worker"), Worker.class);

        String errorPrefix = "Adding Worker Failed: ";

        InfoSet infoSetEvent = selectEvent(worker.getEventID());
        if (infoSetEvent.amountRows() != 1) {
            throw new IOException(errorPrefix + "eventID error - " + worker.getEventID());
        }

        InfoSet infoSetDay = selectDay(worker.getDayID());
        if (infoSetDay.amountRows() != 1) {
            throw new IOException(errorPrefix + "dayID error - " + worker.getDayID());
        }

        InfoSet infoSetService = selectService(worker.getServiceID());
        if (infoSetService.amountRows() != 1) {
            throw new IOException(errorPrefix + "serviceID error - " + worker.getServiceID());
        }

        InfoSet infoSetTask = selectTask(worker.getTaskID());

        ArrayList<Object> eventIDValue = infoSetTask.getColumnValues("eventID");
        ArrayList<Object> dayIDValue = infoSetTask.getColumnValues("dayID");
        ArrayList<Object> serviceIDValue = infoSetTask.getColumnValues("serviceID");
        ArrayList<Object> taskIDValue = infoSetTask.getColumnValues("taskID");

        if ((Integer) eventIDValue.getFirst() == worker.getEventID()
            && (Integer) dayIDValue.getFirst() == worker.getDayID()
            && (Integer) serviceIDValue.getFirst() == worker.getServiceID()
            && (Integer) taskIDValue.getFirst() == worker.getTaskID()) {

            worker.initWorker(worker.getEventID(), worker.getDayID(), worker.getServiceID(), worker.getTaskID());
            worker.workerToDB();
        } else {
            throw new RuntimeException(errorPrefix + "mismatch in a ID");
        }
    }
}
