package org.schichtverwaltung.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    public static void logger (int statusCode, String statusMessage, String jsonString) {
        LocalDateTime timeStamp = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = timeStamp.format(formatter);

        String singleLineJson = jsonString.replaceAll("\\r\\n", "");

        String logMessage = formattedDateTime + " | " + statusCode + " | " + statusMessage + " | " + singleLineJson + "\n";

        System.out.print(logMessage);
        logToFile(logMessage);
    }

    private static void logToFile (String logMessage) {

        String logFilePath = "SchichtverwaltungsBackendApplicationLog.txt";
        File logFile = new File(logFilePath);

        try {
            if (!logFile.exists()) {
                logFile.createNewFile();
                logger(0, "Log File Created " + logFile.getAbsolutePath(), "NO JSON BODY");
                System.out.println("Datei erstellt: " + logFile.getAbsolutePath());
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
                writer.write(logMessage);
            }

        } catch (IOException e) {
            System.err.println("ERROR WITH LOG FILE: " + e.getMessage());
        }
    }
}
