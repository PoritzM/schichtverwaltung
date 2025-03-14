package org.schichtverwaltung.functions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.schichtverwaltung.deserializer.LocalDateDeserializer;
import org.schichtverwaltung.deserializer.LocalTimeDeserializer;
import org.schichtverwaltung.objectStructure.Day;
import org.schichtverwaltung.objectStructure.Event;
import org.schichtverwaltung.objectStructure.Service;
import org.schichtverwaltung.objectStructure.Task;

import java.time.LocalDate;
import java.time.LocalTime;

public class AddShift {

    public static void addShift (String jsonString) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer())
                .create();

        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        Event event = gson.fromJson(jsonObject.getAsJsonObject("event"), Event.class);

        event.initEvent();
        int eventID = event.eventToDB();

        for (Day day : event.getDays()) {
            day.initDay(eventID);
            int dayID = day.dayToDB();

            for (Service service : day.getServices()) {
                service.initService(eventID, dayID);
                int serviceID = service.serviceToDB();

                for (Task task : service.getTasks()) {
                    task.initTask(eventID, dayID, serviceID);
                    int taskID = task.taskToDB();
                }
            }
        }
//        event.print();
    }
}
