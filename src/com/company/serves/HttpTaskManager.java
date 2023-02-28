package com.company.serves;

import com.company.http.KVTaskClient;
import com.company.module.Epic;
import com.company.module.Subtask;
import com.company.module.Task;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class HttpTaskManager extends FileBackedTasksManager{
    private final Gson gson;
    private final KVTaskClient client;

    public HttpTaskManager(String url) {
        super(url);
        gson = Managers.getGson();
        client = new  KVTaskClient(url);
    }

    public void load(String apiToken){
        client.setApiToken(apiToken);
        JsonElement jsonTasks = JsonParser.parseString(client.load("tasks"));
        if (!jsonTasks.isJsonNull()) {
            JsonArray jsonTasksArray = jsonTasks.getAsJsonArray();
            for (JsonElement jsonTask : jsonTasksArray) {
                Task task = gson.fromJson(jsonTask, Task.class);
                addTask(task);
            }
        }

        JsonElement jsonEpics = JsonParser.parseString(client.load("epics"));
        if (!jsonEpics.isJsonNull()) {
            JsonArray jsonEpicsArray = jsonEpics.getAsJsonArray();
            for (JsonElement jsonEpic : jsonEpicsArray) {
                Epic epic = gson.fromJson(jsonEpic, Epic.class);
                addEpic(epic);
            }
        }

        JsonElement jsonSubtasks = JsonParser.parseString(client.load("subtasks"));
        if (!jsonSubtasks.isJsonNull()) {
            JsonArray jsonSubtasksArray = jsonSubtasks.getAsJsonArray();
            for (JsonElement jsonSubtask : jsonSubtasksArray) {
                Subtask subtask = gson.fromJson(jsonSubtask, Subtask.class);
                addSubtask(subtask);
            }
        }

        JsonElement jsonHistoryList = JsonParser.parseString(client.load("history"));
        if (!jsonHistoryList.isJsonNull()) {
            JsonArray jsonHistoryArray = jsonHistoryList.getAsJsonArray();
            for (JsonElement jsonTaskId : jsonHistoryArray) {
                int taskId = jsonTaskId.getAsInt();
                if (collectionSubtask.containsKey(taskId)) {
                    getSubtaskID(taskId);
                } else if (collectionEpic.containsKey(taskId)) {
                    getEpicID(taskId);
                } else if (collectionTask.containsKey(taskId)) {
                    getTaskID(taskId);
                }
            }
        }

    }


    @Override
    public void save() {
        String jsonTasks = gson.toJson(new ArrayList<>(getTasks()));
        client.put("tasks", jsonTasks);
        String jsonEpics = gson.toJson(new ArrayList<>(getEpics()));
        client.put("epics", jsonEpics);
        String jsonSubtasks = gson.toJson(new ArrayList<>(getSubtasks()));
        client.put("subtasks", jsonSubtasks);

        String jsonHistory = gson.toJson(new ArrayList<>(getHistory()));
        client.put("history", jsonHistory);


    }


}
