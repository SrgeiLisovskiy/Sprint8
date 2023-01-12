package com.company.serves;


import java.io.File;
import java.nio.file.Path;

public class Managers {
    public static TaskManager getDefault() {
//        return new InMemoryTaskManager();
        return FileBackedTasksManager.loadFromFile(new File(String.valueOf(Path.of("taskData.csv"))));
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}
