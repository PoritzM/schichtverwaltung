package org.schichtverwaltung.deserializer;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class IgnoreTimestampAdapterFactory implements TypeAdapterFactory {

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);

        return new TypeAdapter<T>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, value);
            }

            @Override
            public T read(JsonReader in) throws IOException {
                if (in.peek() == JsonToken.BEGIN_OBJECT) {
                    in.beginObject();
                    while (in.hasNext()) {
                        String name = in.nextName();
                        if ("timeStamps".equals(name)) {
                            in.skipValue(); // Überspringe das "timeStamps"-Feld
                        } else {
                            // Verarbeite andere Felder normal
                            in.peek(); // Muss aufgerufen werden um den Token zu erhalten.
                        }
                    }
                    in.endObject();

                    // Da wir das Objekt gelesen haben, aber das "timeStamps"-Feld übersprungen haben, müssen wir das Objekt erneut deserialisieren, um die anderen Felder zu erhalten.
                    return gson.fromJson(in, type.getType());
                } else {
                    return delegate.read(in);
                }
            }
        };
    }
}