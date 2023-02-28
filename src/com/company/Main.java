package com.company;

import com.company.http.HttpTaskServer;
import com.company.http.KVServer;
import com.company.http.KVTaskClient;
import com.company.module.Epic;
import com.company.module.Status;
import com.company.module.Subtask;
import com.company.module.Task;
import com.company.serves.HttpTaskManager;
import com.company.serves.Managers;
import com.company.serves.TaskManager;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;


public class Main {
    final public static String URL = "localhost";

    public static void main(String[] args) throws IOException {
        KVServer serv = new KVServer();
        serv.start();
        KVTaskClient client = new KVTaskClient(URL);
        client.setApiToken("DEBUG");
        HttpTaskManager manager = new HttpTaskManager(URL);
        Task task1 = new Task("Задача 1", "Помыть посуду", Status.NEW, Duration.ofMinutes(30), LocalDateTime.now());
        int taskID1 = manager.addTask(task1);
        Task task2 = new Task("Задача 2", "Сделать ТЗ", Status.NEW,Duration.ofMinutes(50),LocalDateTime.now());
        int taskID2 = manager.addTask(task2);
        Epic epic1 = new Epic("Задача 3", "Сделать уроки",Duration.ofMinutes(45),LocalDateTime.now().plusMinutes(47));
        int epicID1 = manager.addEpic(epic1);
        Subtask subtask1 = new Subtask(epicID1, "Подзадача 1", "Сделать физику", Status.NEW, Duration.ofMinutes(25),LocalDateTime.now());
        Subtask subtask2 = new Subtask(epicID1, "Подзадача 2", "Сделать информатику", Status.NEW, Duration.ofMinutes(35),LocalDateTime.now());
        Subtask subtask3 = new Subtask(epicID1, "Подзадача 3", "Сделать математику", Status.NEW, Duration.ofMinutes(43),LocalDateTime.now());
        int subtaskID1 = manager.addSubtask(subtask1);
        int subtaskID2 = manager.addSubtask(subtask2);
        int subtaskID3 = manager.addSubtask(subtask3);
        manager.getTaskID(taskID2);
        manager.getSubtaskID(subtaskID2);
        manager.getTaskID(taskID1);
        manager.getSubtaskID(subtaskID1);
        manager.getEpicID(epicID1);
        System.out.println(manager.getHistory().toString());
        System.out.println(manager.getPrioritizedTasks().size());
        System.out.println("1111111111");
        HttpTaskManager manager1 = new HttpTaskManager(URL);
        manager1.load("DEBUG");
        System.out.println(manager1.getHistory().size());
        System.out.println(manager1.getPrioritizedTasks().toString());

    }
}