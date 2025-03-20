package org.schichtverwaltung.functions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.schichtverwaltung.deserializer.LocalDateDeserializer;
import org.schichtverwaltung.deserializer.LocalTimeDeserializer;
import org.schichtverwaltung.exceptions.BackendException;
import org.schichtverwaltung.objectStructure.Day;
import org.schichtverwaltung.objectStructure.Event;
import org.schichtverwaltung.objectStructure.Service;
import org.schichtverwaltung.objectStructure.Task;
import org.schichtverwaltung.zUtils.ReturnInfos;

import java.time.LocalDate;
import java.time.LocalTime;

public class AddShift {

    public static ReturnInfos doAddShift (String jsonString) throws BackendException {
        return addShift(jsonString);
    }

    private static ReturnInfos addShift (String jsonString) throws BackendException {

        ReturnInfos returnInfos = new ReturnInfos();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer())
                .create();

        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        Event event = gson.fromJson(jsonObject.getAsJsonObject("event"), Event.class);

        event.initEvent();
        int eventID = event.eventToDB();

        if (eventID <= 0) {
            throw new BackendException("Error with creating event");
        }

        returnInfos.addInfo("eventID", eventID);

        for (Day day : event.getDays()) {
            day.initDay(eventID);
            int dayID = day.dayToDB();

            if (dayID <= 0) {
                throw new BackendException("Error with creating day");
            }

            returnInfos.addInfo("dayID", dayID);

            for (Service service : day.getServices()) {
                service.initService(eventID, dayID);
                int serviceID = service.serviceToDB();

                if (serviceID <= 0) {
                    throw new BackendException("Error with creating service");
                }

                returnInfos.addInfo("serviceID", serviceID);

                for (Task task : service.getTasks()) {
                    task.initTask(eventID, dayID, serviceID);
                    int taskID = task.taskToDB();

                    if (taskID <= 0) {
                        throw new BackendException("Error with creating task");
                    }

                    returnInfos.addInfo("taskID", taskID);
                }
            }
        }
        return returnInfos;
    }
}
