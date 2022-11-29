package com.company;

import com.company.module.Epic;
import com.company.module.Status;
import com.company.module.Subtask;
import com.company.module.Task;
import com.company.serves.Manager;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        Task task1 = new Task("Задача 1", "Помыть посуду", Status.NEW);
        Task task2 = new Task("Задача 2", "Сделать ТЗ", Status.NEW);
        manager.addTask(task1);
        manager.addTask(task2);
        List<Integer> subtasks = new ArrayList<>();
        Subtask subtask1 = new Subtask("Подзадача 1", "Сделать физику", Status.NEW);
        Subtask subtask2 = new Subtask("Подзадача 2", "Сделать информатику", Status.NEW);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        subtasks.add(subtask1.getId());
        subtasks.add(subtask2.getId());
        Epic epic1 = new Epic("Задача 3", "Сделать уроки", subtasks);
        manager.addEpic(epic1);
        List<Integer> subtasks1 = new ArrayList<>();
        Subtask subtask3 = new Subtask("Подзадача 1", "Купить хлеб", Status.NEW);
        manager.addSubtask(subtask3);
        subtasks1.add(subtask3.getId());
        Epic epic2 = new Epic("Задача 4", "Сходить в магазин", subtasks1);
        manager.addEpic(epic2);
        System.out.println(task1.toString() + task2.toString() + epic1.toString() + epic2.toString());
        Task task3 = new Task(task1.getName(), task1.getDescription(), Status.DONE);
        task3.setId(task1.getId());
        manager.updateTask(task3);
        System.out.println(manager.collectionTask.get(task1.getId()));
        manager.removeID(0);
        Subtask subtask4 = new Subtask(subtask2.getName(), subtask2.getDescription(), Status.DONE);
        subtask4.setId(subtask2.getId());
        subtask4.setEpicID(subtask2.getEpicID());
        manager.updateSubtask(subtask4);
        System.out.println(epic1);

    }
}