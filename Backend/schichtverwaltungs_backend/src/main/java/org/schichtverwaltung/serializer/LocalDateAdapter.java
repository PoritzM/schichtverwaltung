package org.schichtverwaltung.serializer;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//Um Daten aus LocalDate format in JSON zu übersetzten
public class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public JsonElement serialize(LocalDate localDate, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(localDate));
    }

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return LocalDate.parse(json.getAsString(), formatter);
    }
}

