package org.schichtverwaltung.functions;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.schichtverwaltung.dbTools.InfoSet;
import org.schichtverwaltung.objectStructure.Worker;

import java.io.IOError;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.schichtverwaltung.dbTools.DatabaseTools.sucheWertInMapListe;
import static org.schichtverwaltung.dbTools.SelectMethods.*;

public class AddWorkerToShift {

    public static void addWorkerToShift (String jsonString) throws SQLException, IOException {

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        Worker worker = gson.fromJson(jsonObject.getAsJsonObject("worker"), Worker.class);

        String errorPrefix = "Adding Worker Failed: ";

        Object test;
        sucheWertInMapListe (selectEvent(worker.getEventID()),"eventID", test);


//        InfoSet infoSetEvent = selectEvent(worker.getEventID());
//        if (infoSetEvent.getResultSet().next()) {
//            infoSetEvent.getStatement().close();
//
//            InfoSet infoSetDay = selectDay(worker.getDayID());
//            if (infoSetDay.getResultSet().next()) {
//                infoSetDay.getStatement().close();
//
//                InfoSet infoSetService = selectService(worker.getServiceID());
//                if (infoSetService.getResultSet().next()) {
//                    infoSetService.getStatement().close();
//
//                    InfoSet infoSetTask = selectTask(worker.getTaskID());
//                    if (infoSetTask.getResultSet().next()) {
//                        infoSetTask.getStatement().close();
//
//                        InfoSet infoSet = selectTask(worker.getTaskID());
//                        if (infoSet.getResultSet().getInt("eventID") == worker.getEventID() &&
//                                infoSet.getResultSet().getInt("dayID") == worker.getDayID() &&
//                                infoSet.getResultSet().getInt("serviceID") == worker.getServiceID() &&
//                                infoSet.getResultSet().getInt("taskID") == worker.getTaskID()) {
//
//                            worker.initWorker(worker.getEventID(), worker.getDayID(), worker.getServiceID(), worker.getTaskID());
//                            worker.workerToDB();
//                        } else {
//                            infoSet.getStatement().close();
//                            throw new IOException(errorPrefix + "mismatch in a ID");
//                        }
//                    } else {
//                        infoSetTask.getStatement().close();
//                        throw new IOException(errorPrefix + "taskID not found");
//                    }
//                } else {
//                    infoSetService.getStatement().close();
//                    throw new IOException(errorPrefix + "serviceID not found");
//                }
//            } else {
//                infoSetDay.getStatement().close();
//                throw new IOException(errorPrefix + "dayID not found");
//            }
//        } else {
//            infoSetEvent.getStatement().close();
//            throw new IOException(errorPrefix + "eventID not found - " + worker.getEventID());
//        }
    }
}
