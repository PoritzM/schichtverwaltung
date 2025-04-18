package org.schichtverwaltung.zUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

//Um aus einem String einen LocalDate-Typ zu machen
public class StringToLocalDate {
    public static LocalDate parseLocalDateString(String dateString) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }
}
