package com.company;

import com.company.module.Epic;
import com.company.module.Status;
import com.company.module.Subtask;
import com.company.module.Task;
import com.company.serves.Managers;
import com.company.serves.TaskManager;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {

        TaskManager manager =  Managers.getDefault();

        Task task1 = new Task("Задача 1", "Помыть посуду", Status.NEW);
        Task task2 = new Task("Задача 2", "Сделать ТЗ", Status.NEW);
        int taskID1 = manager.addTask(task1);
        int taskID2 = manager.addTask(task2);
        Epic epic1 = new Epic("Задача 3", "Сделать уроки");
        int epicID1 = manager.addEpic(epic1);
        Subtask subtask1 = new Subtask(epicID1, "Подзадача 1", "Сделать физику", Status.NEW);
        Subtask subtask2 = new Subtask(epicID1, "Подзадача 2", "Сделать информатику", Status.NEW);
        Subtask subtask3 = new Subtask(epicID1, "Подзадача 3", "Сделать математику", Status.NEW);
        int subtaskID1 = manager.addSubtask(subtask1);
        int subtaskID2 = manager.addSubtask(subtask2);
        int subtaskID3 = manager.addSubtask(subtask3);
        Epic epic2 = new Epic("Задача 4", "Сходить в магазин");
        int epicID2 = manager.addEpic(epic2);
        manager.getTaskID(taskID2);
        manager.getSubtaskID(subtaskID2);
        manager.getEpicID(epicID2);
        manager.getTaskID(taskID1);
        manager.getSubtaskID(subtaskID1);
        manager.getSubtaskID(subtaskID3);
        manager.getEpicID(epicID1);
        System.out.println(manager.getHistory());
        manager.getEpicID(epicID2);
        manager.getSubtaskID(subtaskID3);
        manager.getTaskID(taskID1);
        manager.getTaskID(taskID2);
        manager.getSubtaskID(subtaskID2);
        manager.getEpicID(epicID1);
        manager.getSubtaskID(subtaskID1);
        System.out.println(manager.getHistory());
        manager.removeTaskID(taskID2);
        System.out.println(manager.getHistory());
        manager.removeSubtaskID(subtaskID1);
        System.out.println(manager.getHistory());
        manager.removeEpicID(epicID1);

        System.out.println(manager.getHistory());
   }
}