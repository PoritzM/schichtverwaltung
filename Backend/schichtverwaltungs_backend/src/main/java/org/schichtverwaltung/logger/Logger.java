package org.schichtverwaltung.logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    public static void logger (int statusCode, String statusMessage, String jsonString) {
        LocalDateTime timeStamp = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = timeStamp.format(formatter);

        String singleLineJson = jsonString.replaceAll("\\r\\n", "");

        System.out.println(formattedDateTime + " | " + statusCode + " | " + statusMessage + " | " + singleLineJson);
    }
}
