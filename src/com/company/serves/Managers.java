package com.company.serves;


import com.company.adapters.DurationTypeAdapter;
import com.company.adapters.LocalDateTimeTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;

public class Managers {
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
//        return FileBackedTasksManager.loadFromFile(new File("taskData.csv"));
//        return new HttpTaskManager("localhost");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static HttpTaskManager getDefaultJson(String url){
        return new HttpTaskManager(url);
    }
    public static Gson getGson(){
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .registerTypeAdapter(Duration.class, new DurationTypeAdapter());
        return gsonBuilder.create();
    }

}
