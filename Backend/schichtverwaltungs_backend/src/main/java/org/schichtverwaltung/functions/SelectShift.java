package org.schichtverwaltung.functions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.schichtverwaltung.dbTools.InfoSet;
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

public class SelectShift {

    public static void test () throws SQLException, ParseException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        gsonBuilder.registerTypeAdapter(LocalTime.class, new LocalTimeAdapter());
        Gson gson = gsonBuilder.create();

        Event event = selectShift(51);

        String json = gson.toJson(event);

        System.out.println(json);
    }

    public static Event selectShift (int eventID) throws SQLException, ParseException {

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

    public static void selectAllShifts () throws SQLException, ParseException {

        InfoSet infoSetEvent = selectTableAll("events");

        ArrayList<Object> eventIDs = infoSetEvent.getColumnValues("eventID");
        ArrayList<Event> events = new ArrayList<>();

        for (Object eventID : eventIDs) {
            events.add(selectShift((Integer) eventID));
        }

        for (Event event : events) {
            event.print();
        }
    }

    private static Event selectEvent (int eventID) throws SQLException, ParseException {
        InfoSet infoSetEvent = selectTable("eventID",String.valueOf(eventID), "events");

        ArrayList<Object> eventNames = infoSetEvent.getColumnValues("eventName");
        ArrayList<Object> showEvents = infoSetEvent.getColumnValues("showEvent");
        ArrayList<Object> registerOnEvents = infoSetEvent.getColumnValues("registerOnEvent");
        ArrayList<Object> timeStampsCreate = infoSetEvent.getColumnValues("timeStampCreate");
        ArrayList<Object> timeStampsEdit = infoSetEvent.getColumnValues("timeStampEdit");

        return new Event(eventID,(String) eventNames.getFirst(), Boolean.parseBoolean((String) showEvents.getFirst()), Boolean.parseBoolean((String) registerOnEvents.getFirst()), new TimeStamps(parseDateString((String) timeStampsCreate.getFirst()),parseDateString((String) timeStampsEdit.getFirst())));
    }

    private static Day selectDay (int dayID) throws SQLException, ParseException {
        InfoSet infoSetEvent = selectTable("dayID",String.valueOf(dayID), "days");

        ArrayList<Object> eventIDs = infoSetEvent.getColumnValues("eventID");
        ArrayList<Object> days = infoSetEvent.getColumnValues("day");
        ArrayList<Object> timeStampsCreate = infoSetEvent.getColumnValues("timeStampCreate");
        ArrayList<Object> timeStampsEdit = infoSetEvent.getColumnValues("timeStampEdit");

        return new Day((Integer) eventIDs.getFirst(),dayID, parseLocalDateString((String) days.getFirst()), new TimeStamps(parseDateString((String) timeStampsCreate.getFirst()),parseDateString((String) timeStampsEdit.getFirst())));
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

        return new Service((Integer) eventIDs.getFirst(), (Integer) dayIDs.getFirst(),serviceID , (String) serviceDescriptions.getFirst(), parseLocalTimeString((String) timeStarts.getFirst()), parseLocalTimeString((String) timeEnds.getFirst()),new TimeStamps(parseDateString((String) timeStampsCreate.getFirst()),parseDateString((String) timeStampsEdit.getFirst())));
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

        return new Task((Integer) eventIDs.getFirst(), (Integer) dayIDs.getFirst(), (Integer) serviceIDs.getFirst(), taskID, (String) taskDescriptions.getFirst(), (Integer) neededWorkers.getFirst(), new TimeStamps(parseDateString((String) timeStampsCreate.getFirst()),parseDateString((String) timeStampsEdit.getFirst())));
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

        return new Worker((Integer) eventIDs.getFirst(), (Integer) dayIDs.getFirst(), (Integer) serviceIDs.getFirst(), (Integer) taskIDs.getFirst(), workerID, (String) workerNames.getFirst(), new TimeStamps(parseDateString((String) timeStampsCreate.getFirst()),parseDateString((String) timeStampsEdit.getFirst())));
    }
}
