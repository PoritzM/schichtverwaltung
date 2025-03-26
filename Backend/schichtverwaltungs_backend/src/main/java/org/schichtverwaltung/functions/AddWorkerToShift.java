package org.schichtverwaltung.functions;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.schichtverwaltung.dbTools.InfoSet;
import org.schichtverwaltung.exceptions.BackendException;
import org.schichtverwaltung.exceptions.BlockedActionException;
import org.schichtverwaltung.exceptions.ItemNotFoundException;
import org.schichtverwaltung.objectStructure.Worker;
import org.schichtverwaltung.zUtils.ReturnInfos;

import java.io.IOError;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.schichtverwaltung.dbTools.SelectMethods.*;

//Hinzufügen einer Person zu einer Schicht
public class AddWorkerToShift {

    //Ausführer der Methode zum Catchen und Verwalten von Exceptions
    public static ReturnInfos doAddWorkerToShift (String jsonString) throws BackendException, BlockedActionException {
        try {
            return addWorkerToShift(jsonString);
        } catch (SQLException | IOException exception) {
            throw new BackendException("OHH FUCK " + exception.getMessage());
        }
    }

    private static ReturnInfos addWorkerToShift (String jsonString) throws SQLException, IOException, BackendException, BlockedActionException, ItemNotFoundException {

        int workerID = -1;
        ReturnInfos returnInfos = new ReturnInfos();

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        Worker worker = gson.fromJson(jsonObject.getAsJsonObject("worker"), Worker.class);

        InfoSet infoSetEvent = selectTable("eventID",String.valueOf(worker.getEventID()), "events");

        if (infoSetEvent.amountRows() != 1) {
            throw new ItemNotFoundException("eventID " + worker.getEventID() + " not in Database");
        }

        ArrayList<Object> checkRegisterOnEvent = infoSetEvent.getColumnValues("registerOnEvent");
        if (!Boolean.parseBoolean((String) checkRegisterOnEvent.get(0))) {
            throw new BlockedActionException("it is not allowed to register on this event! (" + worker.getEventID() + ")");
        }

        InfoSet infoSetDay = selectTable("dayID", String.valueOf(worker.getDayID()), "days");
        if (infoSetDay.amountRows() != 1) {
            throw new ItemNotFoundException("dayID " + worker.getDayID() + " not in Database");
        }

        InfoSet infoSetService = selectTable("serviceID", String.valueOf(worker.getServiceID()), "services");
        if (infoSetService.amountRows() != 1) {
            throw new ItemNotFoundException("serviceID " + worker.getServiceID() + " not in Database");
        }

        InfoSet infoSetTask = selectTable("taskID", String.valueOf(worker.getTaskID()), "tasks");

        ArrayList<Object> eventIDValue = infoSetTask.getColumnValues("eventID");
        ArrayList<Object> dayIDValue = infoSetTask.getColumnValues("dayID");
        ArrayList<Object> serviceIDValue = infoSetTask.getColumnValues("serviceID");
        ArrayList<Object> taskIDValue = infoSetTask.getColumnValues("taskID");

        if ((Integer) eventIDValue.get(0) == worker.getEventID()
            && (Integer) dayIDValue.get(0) == worker.getDayID()
            && (Integer) serviceIDValue.get(0) == worker.getServiceID()
            && (Integer) taskIDValue.get(0) == worker.getTaskID()) {

            worker.initWorker(worker.getEventID(), worker.getDayID(), worker.getServiceID(), worker.getTaskID());
            workerID = worker.workerToDB();
            returnInfos.addInfo("WorkerID", workerID);

        } else {
            throw new BackendException("mismatch in a ID from Task entry vs new Worker");
        }

        return returnInfos;
    }
}
