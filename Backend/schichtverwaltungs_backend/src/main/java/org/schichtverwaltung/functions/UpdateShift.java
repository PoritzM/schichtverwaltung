package org.schichtverwaltung.functions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.schichtverwaltung.dbTools.InfoSet;
import org.schichtverwaltung.deserializer.*;
import org.schichtverwaltung.exceptions.BackendException;
import org.schichtverwaltung.exceptions.ItemNotFoundException;
import org.schichtverwaltung.objectStructure.Day;
import org.schichtverwaltung.objectStructure.Event;
import org.schichtverwaltung.objectStructure.Service;
import org.schichtverwaltung.objectStructure.Task;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import static org.schichtverwaltung.dbTools.RemoveMethods.removeEntire;
import static org.schichtverwaltung.dbTools.SelectMethods.selectTable;

public class UpdateShift {

    public static void doUpdateShift (String jsonString) throws BackendException {
        try {
            updateShift(jsonString);
        } catch (SQLException exception) {
            throw new BackendException(exception.getMessage());
        }
    }

    private static void updateShift (String jsonString) throws SQLException, BackendException {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .registerTypeAdapter(LocalTime.class, new LocalTimeDeserializerWithSeconds())
                .registerTypeAdapter(Date.class, new DateDeserializer())
                .create();

        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        Event event = gson.fromJson(jsonObject.getAsJsonObject("event"), Event.class);

        //Test ob alles past
        checkShift(event);

        //Updates durchführen
        updateShift(event);

        //Einträge löschen
        deleteShift(event);

        //Einträge hinzufügen

    }

    private static void checkShift (Event event) throws BackendException, SQLException {

        InfoSet infoSetEvent = selectTable("eventID", String.valueOf(event.getEventID()), "events");
        if (infoSetEvent.getColumnValues("eventID").isEmpty()){
            throw new ItemNotFoundException("EventID " + event.getEventID() + " not found!)");
        }

        for (Day day : event.getDays()) {

            InfoSet infoSetDay = selectTable("dayID", String.valueOf(day.getDayID()), "days");
            if (infoSetDay.getColumnValues("dayID").isEmpty()){
                throw new ItemNotFoundException("DayID " + day.getDayID() + " not found!)");
            }
            if((Integer) infoSetDay.getColumnValues("dayID").get(0) != day.getDayID() ||
                    (Integer) infoSetDay.getColumnValues("eventID").get(0) != event.getEventID()) {
                throw new BackendException("Missmatch in a ID (Day Object vs Day in DB)");
            }

            for(Service service : day.getServices()) {

                InfoSet infoSetService = selectTable("serviceID", String.valueOf(service.getServiceID()), "services");
                if (infoSetService.getColumnValues("serviceID").isEmpty()){
                    throw new ItemNotFoundException("ServiceID " + service.getServiceID() + " not found!)");
                }
                if((Integer) infoSetService.getColumnValues("serviceID").get(0) != service.getServiceID() ||
                        (Integer) infoSetService.getColumnValues("dayID").get(0) != day.getDayID() ||
                        (Integer) infoSetService.getColumnValues("eventID").get(0) != event.getEventID()) {
                    throw new BackendException("Missmatch in a ID (Service Object vs Service in DB)");
                }
                for(Task task : service.getTasks()) {

                    InfoSet infoSetTask = selectTable("taskID", String.valueOf(task.getTaskID()), "tasks");
                    if (infoSetTask.getColumnValues("taskID").isEmpty()){
                        throw new ItemNotFoundException("TaskID " + task.getTaskID() + " not found!)");
                    }

                    if((Integer) infoSetTask.getColumnValues("taskID").get(0) != task.getTaskID() ||
                            (Integer) infoSetTask.getColumnValues("serviceID").get(0) != service.getServiceID() ||
                            (Integer) infoSetTask.getColumnValues("dayID").get(0) != day.getDayID() ||
                            (Integer) infoSetTask.getColumnValues("eventID").get(0) != event.getEventID()) {
                        throw new BackendException("Missmatch in a ID (Task Object vs Task in DB)");
                    }
                }
            }
        }
    }

    private static void updateShift (Event event) throws SQLException, BackendException {

        event.updateEventOnDB();
        for (Day day : event.getDays()) {
            if(day.getDayID() != -1) {
                day.updateDayOnDB();
            }
            for(Service service : day.getServices()) {
                if(service.getServiceID() != -1) {
                    service.updateServiceOnDB();
                }
                for(Task task : service.getTasks()) {
                    if(task.getTaskID() != -1) {
                        task.updateTaskOnDB();
                    }
                }
            }
        }
    }

    private static void deleteShift (Event event) throws SQLException, BackendException {

        InfoSet infoSetDay = selectTable("eventID", String.valueOf(event.getEventID()), "days");
        ArrayList<Integer> dayIDsFromDB = infoSetDay.getColumnValuesAsInt("dayID");
        ArrayList<Integer> daysInBoth = new ArrayList<>();

        for (Day day : event.getDays()) {

            if (dayIDsFromDB.contains(day.getDayID())) {
                daysInBoth.add(day.getDayID());
            }

            InfoSet infoSetService = selectTable("dayID", String.valueOf(day.getDayID()), "services");
            ArrayList<Integer> serviceIDsFromDB = infoSetService.getColumnValuesAsInt("serviceID");
            ArrayList<Integer> servicesInBoth = new ArrayList<>();

            for(Service service : day.getServices()) {

                if (serviceIDsFromDB.contains(service.getServiceID())) {
                    servicesInBoth.add(service.getServiceID());
                }

                InfoSet infoSetTask = selectTable("serviceID", String.valueOf(service.getServiceID()), "tasks");
                ArrayList<Integer> taskIDsFromDB = infoSetTask.getColumnValuesAsInt("taskID");
                ArrayList<Integer> tasksInBoth = new ArrayList<>();

                for(Task task : service.getTasks()) {

                    if (taskIDsFromDB.contains(task.getTaskID())) {
                        tasksInBoth.add(task.getTaskID());
                    }
                }

                //DEL tasks
                for (Integer taskIDFromDB : taskIDsFromDB) {
                    if (!tasksInBoth.contains(taskIDFromDB)) {
                        removeEntire("tasks", "taskID", String.valueOf(taskIDFromDB));
                    }
                }
            }

            //DEL services
            for (Integer serviceIDFromDB : serviceIDsFromDB) {
                if (!servicesInBoth.contains(serviceIDFromDB)) {
                    removeEntire("services", "serviceID", String.valueOf(serviceIDFromDB));
                }
            }
        }

        //DEL days
        for (Integer dayIDFromDB : dayIDsFromDB) {
            if (!daysInBoth.contains(dayIDFromDB)) {
                removeEntire("days", "dayID", String.valueOf(dayIDFromDB));
            }
        }
    }

    private static void insertShift (Event event) {
        for (Day day : event.getDays()) {
            if(day.getDayID() == -1) {

            }
            for(Service service : day.getServices()) {
                if(service.getServiceID() == -1) {

                }
                for(Task task : service.getTasks()) {

                    if(task.getTaskID() == -1) {

                    }
                }
            }
        }
    }
}
