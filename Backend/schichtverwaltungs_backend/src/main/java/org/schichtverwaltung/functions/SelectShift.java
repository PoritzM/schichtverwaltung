package org.schichtverwaltung.functions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.schichtverwaltung.dbTools.InfoSet;
import org.schichtverwaltung.exceptions.BackendException;
import org.schichtverwaltung.exceptions.ItemNotFoundException;
import org.schichtverwaltung.objectStructure.*;
import org.schichtverwaltung.serializer.LocalDateAdapter;
import org.schichtverwaltung.serializer.LocalTimeAdapter;
import org.schichtverwaltung.zUtils.TimeStamps;

import java.sql.Array;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.schichtverwaltung.dbTools.SelectMethods.selectTable;
import static org.schichtverwaltung.dbTools.SelectMethods.selectTableAll;
import static org.schichtverwaltung.zUtils.StringToDateParser.parseDateString;
import static org.schichtverwaltung.zUtils.StringToLocalDate.parseLocalDateString;
import static org.schichtverwaltung.zUtils.StringToLocalTime.parseLocalTimeString;

//"Lesen" der Daten aus der Datenbank zu einer Kompletten Schicht oder nur Teil Infos zu allen Schichten
public class SelectShift {

    //--------------------------------------------------------
    //Methoden welche alle Informationen zu einer Schicht holt
    //--------------------------------------------------------

    //Ausführer der Methode zum Catchen und Verwalten von Exceptions
    public static String doSelectShift (int eventID) throws BackendException, ItemNotFoundException {
        try {
            return getJsonFromSelectedShift(eventID);
        } catch (SQLException | ParseException exception) {
            throw new BackendException("OHHHHH MEGA FUCK " + exception.getMessage());
        }
    }

    //Objekte in Json umwandeln
    public static String getJsonFromSelectedShift (int eventID) throws SQLException, ParseException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeAdapter());
        Gson gson = gsonBuilder.create();

        Event event = selectShift(eventID);

        return gson.toJson(event);
    }

    public static Event selectShift (int eventID) throws SQLException, ParseException {

        InfoSet checkForEvent = selectTable("eventID", String.valueOf(eventID), "events");

        if(checkForEvent.getColumnValues("eventID").isEmpty()) {
            throw new ItemNotFoundException("EventID " + eventID + " not found!");
        }

        Event event = selectEvent(eventID);

        InfoSet infoSetDays = selectTable("eventID", String.valueOf(eventID), "days");
        ArrayList<Object> dayIDs = infoSetDays.getColumnValues("dayID");

        int dayIndex = 0;
        for (Object dayID : dayIDs) {
            event.addDay(selectDay((Integer) dayID));

            InfoSet infoSetServices = selectTable("dayID", String.valueOf(dayID), "services");
            ArrayList<Object> serviceIDs = infoSetServices.getColumnValues("serviceID");

            int serviceIndex = 0;
            for (Object servicesID : serviceIDs) {
                event.getDays().get(dayIndex).addService(selectService((Integer) servicesID));

                InfoSet infoSetTasks = selectTable("serviceID", String.valueOf(servicesID), "tasks");
                ArrayList<Object> taskIDs = infoSetTasks.getColumnValues("taskID");

                int taskIndex = 0;
                for (Object taskID : taskIDs) {
                    event.getDays().get(dayIndex).getServices().get(serviceIndex).addTask(selectTask((Integer) taskID));

                    InfoSet infoSetWorker = selectTable("taskID", String.valueOf(taskID), "worker");
                    ArrayList<Object> workerIDs = infoSetWorker.getColumnValues("workerID");

                    for (Object workerID : workerIDs) {
                        event.getDays().get(dayIndex).getServices().get(serviceIndex).getTasks().get(taskIndex).addWorker(selectWorker((Integer) workerID));
                    }
                    taskIndex++;
                }
                serviceIndex++;
            }
            dayIndex++;
        }
        return event;
    }

    //------------------------------------------------
    //Methoden welche zu allen Schichten die Tage holt
    //------------------------------------------------

    //Ausführer der Methode zum Catchen und Verwalten von Exceptions
    public static String doSelectShiftOverview () throws BackendException {
        try {
            return getJsonFormShiftOverview();
        } catch (SQLException | ParseException exception) {
            throw new BackendException("OHHHHH MEGA FUCK " + exception.getMessage());
        }
    }

    //Objekte in Json umwandeln
    public static String getJsonFormShiftOverview () throws SQLException, ParseException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeAdapter());
        Gson gson = gsonBuilder.create();

        ArrayList<Event> events = selectShiftOverview();

        return gson.toJson(events);
    }

    public static ArrayList<Event> selectShiftOverview () throws SQLException, ParseException {

        InfoSet infoSetEvent = selectTableAll("events");

        ArrayList<Object> eventIDs = infoSetEvent.getColumnValues("eventID");
        ArrayList<Event> events = new ArrayList<>();

        for (Object eventID : eventIDs) {

            Event event = selectEvent((Integer) eventID);

            InfoSet infoSetDays = selectTable("eventID", String.valueOf(eventID), "days");
            ArrayList<Object> dayIDs = infoSetDays.getColumnValues("dayID");

            for (Object dayID : dayIDs) {
                event.addDay(selectDay((Integer) dayID));
            }
            events.add(event);
        }
        return events;
    }

    //---------------------------------------------------------------
    //Holt die Daten der einzelnen hierarchiestufen aus der Datenbank
    //---------------------------------------------------------------

    private static Event selectEvent (int eventID) throws SQLException, ParseException, ItemNotFoundException {
        InfoSet infoSetEvent = selectTable("eventID",String.valueOf(eventID), "events");

        ArrayList<Object> eventNames = infoSetEvent.getColumnValues("eventName");
        ArrayList<Object> showEvents = infoSetEvent.getColumnValues("showEvent");
        ArrayList<Object> registerOnEvents = infoSetEvent.getColumnValues("registerOnEvent");
        ArrayList<Object> timeStampsCreate = infoSetEvent.getColumnValues("timeStampCreate");
        ArrayList<Object> timeStampsEdit = infoSetEvent.getColumnValues("timeStampEdit");

        return new Event(eventID,(String) eventNames.get(0), Boolean.parseBoolean((String) showEvents.get(0)), Boolean.parseBoolean((String) registerOnEvents.get(0)), new TimeStamps(parseDateString((String) timeStampsCreate.get(0)),parseDateString((String) timeStampsEdit.get(0))));
    }

    private static Day selectDay (int dayID) throws SQLException, ParseException {
        InfoSet infoSetEvent = selectTable("dayID",String.valueOf(dayID), "days");

        ArrayList<Object> eventIDs = infoSetEvent.getColumnValues("eventID");
        ArrayList<Object> days = infoSetEvent.getColumnValues("day");
        ArrayList<Object> timeStampsCreate = infoSetEvent.getColumnValues("timeStampCreate");
        ArrayList<Object> timeStampsEdit = infoSetEvent.getColumnValues("timeStampEdit");

        return new Day((Integer) eventIDs.get(0),dayID, parseLocalDateString((String) days.get(0)), new TimeStamps(parseDateString((String) timeStampsCreate.get(0)),parseDateString((String) timeStampsEdit.get(0))));
    }

    private static Service selectService (int serviceID) throws SQLException, ParseException {
        InfoSet infoSetService = selectTable("serviceID", String.valueOf(serviceID), "services");

        ArrayList<Object> eventIDs = infoSetService.getColumnValues("eventID");
        ArrayList<Object> dayIDs = infoSetService.getColumnValues("dayID");
        ArrayList<Object> serviceDescriptions = infoSetService.getColumnValues("serviceDescription");
        ArrayList<Object> timeStarts = infoSetService.getColumnValues("timeStart");
        ArrayList<Object> timeEnds = infoSetService.getColumnValues("timeEnd");
        ArrayList<Object> timeStampsCreate = infoSetService.getColumnValues("timeStampCreate");
        ArrayList<Object> timeStampsEdit = infoSetService.getColumnValues("timeStampEdit");

        return new Service((Integer) eventIDs.get(0), (Integer) dayIDs.get(0),serviceID , (String) serviceDescriptions.get(0), parseLocalTimeString((String) timeStarts.get(0)), parseLocalTimeString((String) timeEnds.get(0)),new TimeStamps(parseDateString((String) timeStampsCreate.get(0)),parseDateString((String) timeStampsEdit.get(0))));
    }

    private static Task selectTask (int taskID) throws SQLException, ParseException {
        InfoSet infoSetTask = selectTable("taskID", String.valueOf(taskID), "tasks");

        ArrayList<Object> eventIDs = infoSetTask.getColumnValues("eventID");
        ArrayList<Object> dayIDs = infoSetTask.getColumnValues("dayID");
        ArrayList<Object> serviceIDs = infoSetTask.getColumnValues("serviceID");
        ArrayList<Object> taskDescriptions = infoSetTask.getColumnValues("taskDescription");
        ArrayList<Object> neededWorkers = infoSetTask.getColumnValues("neededWorker");
        ArrayList<Object> timeStampsCreate = infoSetTask.getColumnValues("timeStampCreate");
        ArrayList<Object> timeStampsEdit = infoSetTask.getColumnValues("timeStampEdit");

        return new Task((Integer) eventIDs.get(0), (Integer) dayIDs.get(0), (Integer) serviceIDs.get(0), taskID, (String) taskDescriptions.get(0), (Integer) neededWorkers.get(0), new TimeStamps(parseDateString((String) timeStampsCreate.get(0)),parseDateString((String) timeStampsEdit.get(0))));
    }

    private static Worker selectWorker (int workerID) throws SQLException, ParseException {
        InfoSet infoSetWorker = selectTable("workerID", String.valueOf(workerID), "worker");

        ArrayList<Object> eventIDs = infoSetWorker.getColumnValues("eventID");
        ArrayList<Object> dayIDs = infoSetWorker.getColumnValues("dayID");
        ArrayList<Object> serviceIDs = infoSetWorker.getColumnValues("serviceID");
        ArrayList<Object> taskIDs = infoSetWorker.getColumnValues("taskID");
        ArrayList<Object> workerNames = infoSetWorker.getColumnValues("workerName");
        ArrayList<Object> timeStampsCreate = infoSetWorker.getColumnValues("timeStampCreate");
        ArrayList<Object> timeStampsEdit = infoSetWorker.getColumnValues("timeStampEdit");

        return new Worker((Integer) eventIDs.get(0), (Integer) dayIDs.get(0), (Integer) serviceIDs.get(0), (Integer) taskIDs.get(0), workerID, (String) workerNames.get(0), new TimeStamps(parseDateString((String) timeStampsCreate.get(0)),parseDateString((String) timeStampsEdit.get(0))));
    }
}
