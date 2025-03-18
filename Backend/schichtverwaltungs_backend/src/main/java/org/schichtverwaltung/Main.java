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
import static org.schichtverwaltung.functions.SelectShift.test;
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
                "    \"eventID\": 51,\n" +
                "    \"dayID\": 74,\n" +
                "    \"serviceID\": 67,\n" +
                "    \"taskID\": 162,\n" +
                "    \"workerName\": \"Max Mustermann\"\n" +
                "  }\n" +
                "}";

        String jsonStringWorker2 = "{\n" +
                "  \"worker\": {\n" +
                "    \"eventID\": 52,\n" +
                "    \"dayID\": 77,\n" +
                "    \"serviceID\": 70,\n" +
                "    \"taskID\": 170,\n" +
                "    \"workerName\": \"Geiler Typ\"\n" +
                "  }\n" +
                "}";

//        addShift(jsonString);
        try {
//            addWorkerToShift(jsonStringWorker);
//            addWorkerToShift(jsonStringWorker2);
//            removeWorkerFromShift(8);
//            removeShift(50);
//            updateShowEvent(51, false);
//            updateRegisterOnEvent(51, false);
//            selectAllShifts();
            test();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}