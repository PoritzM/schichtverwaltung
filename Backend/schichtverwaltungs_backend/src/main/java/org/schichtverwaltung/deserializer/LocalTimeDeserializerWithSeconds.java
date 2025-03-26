package org.schichtverwaltung.deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

//Um Daten aus JSON in ein LocalTime format zu verwandeln (beachtet die Sekunden!)
public class LocalTimeDeserializerWithSeconds implements JsonDeserializer<LocalTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String timeString = json.getAsString();
        return LocalTime.parse(timeString, FORMATTER);
    }
}