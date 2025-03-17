package org.schichtverwaltung;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.schichtverwaltung.deserializer.LocalDateDeserializer;
import org.schichtverwaltung.deserializer.LocalTimeDeserializer;
import org.schichtverwaltung.objectStructure.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.schichtverwaltung.functions.AddShift.addShift;
import static org.schichtverwaltung.functions.AddWorkerToShift.addWorkerToShift;
import static org.schichtverwaltung.functions.RemoveShift.removeShift;
import static org.schichtverwaltung.functions.RemoveWorkerFromShift.removeWorkerFromShift;
import static org.schichtverwaltung.functions.SelectShift.selectAllShifts;
import static org.schichtverwaltung.functions.UpdateEvent.updateRegisterOnEvent;
import static org.schichtverwaltung.functions.UpdateEvent.updateShowEvent;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {


        String jsonString = "{\n" +
                "  \"event\": {\n" +
                "    \"eventName\": \"Stadtfest 2024\",\n" +
                "    \"showEvent\": true,\n" +
                "    \"registerOnEvent\": true,\n" +
                "    \"days\": [\n" +
                "      {\n" +
                "        \"day\": \"2024-07-11\",\n" +
                "        \"services\": [\n" +
                "          {\n" +
                "            \"serviceDescription\": \"Aufbauen\",\n" +
                "            \"timeStart\": \"11:00\",\n" +
                "            \"timeEnd\": \"13:00\",\n" +
                "            \"tasks\": [\n" +
                "              {\n" +
                "                \"taskDescription\": \"Teke Aufbauen\",\n" +
                "                \"neededWorker\": 3\n" +
                "              },\n" +
                "              {\n" +
                "                \"taskDescription\": \"Getränke Abholen\",\n" +
                "                \"neededWorker\": 1\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      {\n" +
                "        \"day\": \"2024-07-12\",\n" +
                "        \"services\": [\n" +
                "          {\n" +
                "            \"serviceDescription\": \"Dienst 1\",\n" +
                "            \"timeStart\": \"13:00\",\n" +
                "            \"timeEnd\": \"11:00\",\n" +
                "            \"tasks\": [\n" +
                "              {\n" +
                "                \"taskDescription\": \"Spülen\",\n" +
                "                \"neededWorker\": 3\n" +
                "              },\n" +
                "              {\n" +
                "                \"taskDescription\": \"Bier Zapfen\",\n" +
                "                \"neededWorker\": 1\n" +
                "              },\n" +
                "              {\n" +
                "                \"taskDescription\": \"Bedienen\",\n" +
                "                \"neededWorker\": 5\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        String jsonStringWorker = "{\n" +
                "  \"worker\": {\n" +
                "    \"eventID\": 50,\n" +
                "    \"dayID\": 72,\n" +
                "    \"serviceID\": 65,\n" +
                "    \"taskID\": 157,\n" +
                "    \"workerName\": \"Max Mustermann\"\n" +
                "  }\n" +
                "}";

//        addShift(jsonString);
        try {
//            addWorkerToShift(jsonStringWorker);
//            removeWorkerFromShift(8);
//            removeShift(50);
//            updateShowEvent(51, false);
//            updateRegisterOnEvent(51, false);
            selectAllShifts();
        } catch (Exception e) {
            System.out.println(e);
        }

//        Gson gson = new GsonBuilder()
//                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
//                .registerTypeAdapter(LocalTime.class, new LocalTimeDeserializer())
//                .create();
//
//        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
//        Event event = gson.fromJson(jsonObject.getAsJsonObject("event"), Event.class);
//
//
//        //
//
//        event.initEvent();
//        int eventID = event.eventToDB();
//
//        for (Day day : event.getDays()) {
//            day.initDay(eventID);
//            int dayID = day.dayToDB();
//
//            for (Service service : day.getServices()) {
//                service.initService(eventID, dayID);
//                int serviceID = service.serviceToDB();
//
//                for (Task task : service.getTasks()) {
//                    task.initTask(eventID, dayID, serviceID);
//                    int taskID = task.taskToDB();
//                }
//            }
//        }
//
//        event.print();

    }
}