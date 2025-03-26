package org.schichtverwaltung.zUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//Um aus einem String einen Date-Typ zu machen
public class StringToDateParser {

    public static Date parseDateString(String dateString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        return format.parse(dateString);
    }
}
