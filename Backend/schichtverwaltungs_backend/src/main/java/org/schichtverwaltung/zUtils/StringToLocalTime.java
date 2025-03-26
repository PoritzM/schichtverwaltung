package org.schichtverwaltung.zUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

//Um aus einem String einen LocalTime-Typ zu machen
public class StringToLocalTime {
    public static LocalTime parseLocalTimeString(String timeString) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(timeString, formatter);
    }
}
