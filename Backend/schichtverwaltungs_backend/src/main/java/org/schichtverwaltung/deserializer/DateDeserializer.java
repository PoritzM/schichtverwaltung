package org.schichtverwaltung.deserializer;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateDeserializer implements JsonDeserializer<Date> {

    private final SimpleDateFormat dateFormat;

    public DateDeserializer() {
        dateFormat = new SimpleDateFormat("MMM dd, yyyy, h:mm:ss a", Locale.ENGLISH);
    }

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            return dateFormat.parse(json.getAsString());
        } catch (ParseException e) {
            throw new JsonParseException(e);
        }
    }
}