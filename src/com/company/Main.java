package com.company;

import com.company.module.Epic;
import com.company.module.Status;
import com.company.module.Subtask;
import com.company.module.Task;
import com.company.serves.Manager;


public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        Task task1 = new Task("Задача 1", "Помыть посуду", Status.NEW);
        Task task2 = new Task("Задача 2", "Сделать ТЗ", Status.NEW);
        manager.addTask(task1);
        manager.addTask(task2);
        Epic epic1 = new Epic("Задача 3", "Сделать уроки");
        int epicID1 = manager.addEpic(epic1);
        Subtask subtask1 = new Subtask(epicID1, "Подзадача 1", "Сделать физику", Status.NEW);
        Subtask subtask2 = new Subtask(epicID1, "Подзадача 2", "Сделать информатику", Status.NEW);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        Epic epic2 = new Epic("Задача 4", "Сходить в магазин");
        int epicID2 = manager.addEpic(epic2);
        Subtask subtask3 = new Subtask(epicID2, "Подзадача 1", "Купить хлеб", Status.NEW);
        manager.addSubtask(subtask3);
        System.out.println(manager.getSubtasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getTasks());
    Task task3 = new Task("Задача 1", "Помыть посуду",1, Status.DONE);
        manager.updateTask(task3);
        System.out.println(manager.getTasks());
        Subtask subtask4 = new Subtask("Подзадача 1", "Сделать физику",4, Status.DONE);
        manager.updateSubtask(subtask4);
      System.out.println(manager.getSubtasks());
        System.out.println(manager.getEpics());
      manager.clearSubtask();
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());
       manager.removeTaskID(2);
       System.out.println(manager.getTasks());
        manager.removeEpicID(6);
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());
   }
}