package com.company.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Duration;

//Класс-адаптер для преобразования продолжительности в json и обратно
public class DurationTypeAdapter extends TypeAdapter<Duration> {
    @Override
    public void write(final JsonWriter jsonWriter, final Duration duration) throws IOException {
        if (duration != null)
            jsonWriter.value(duration.toString());
        else
            jsonWriter.nullValue();
    }

    @Override
    public Duration read(final JsonReader jsonReader) throws IOException {
        return Duration.parse(jsonReader.nextString());
    }
}