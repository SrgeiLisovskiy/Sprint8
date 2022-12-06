package com.company.serves;

import com.company.module.Epic;
import com.company.module.Status;
import com.company.module.Subtask;
import com.company.module.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


public class Manager {
    private int id = 1;


    public HashMap<Integer, Task> collectionTask = new HashMap<>();
    public HashMap<Integer, Epic> collectionEpic = new HashMap<>();
    public HashMap<Integer, Subtask> collectionSubtask = new HashMap<>();


    public void addTask(Task task) {                     // добавляем Task
        task.setId(id++);
        collectionTask.put(task.getId(), task);

    }

    public int addEpic(Epic epic) {                                //Добавляем Epic
        List<Integer>subtaskID = new ArrayList<>();
        epic.setSubtasksID(subtaskID);
        epic.setId(id++);
        epic.setStatus(Status.NEW);
            collectionEpic.put(epic.getId(), epic);
        return epic.getId();
    }

    public void addSubtask(Subtask subtask) {                       //Добавляем Subtask
        subtask.setId(id++);
        collectionSubtask.put(subtask.getId(), subtask);
        List<Integer> subtaskID = collectionEpic.get(subtask.getEpicID()).getSubtasks();
        subtaskID.add(subtask.getId());
        updateEpicStatus(collectionEpic.get(subtask.getEpicID()));
    }




    public List<Task> getTasks() {                              //Вывод списка Task
        List<Task> tasks = new ArrayList<>(collectionTask.values());
        return tasks;
    }

    public List<Subtask> getSubtasks() {                        //Вывод списка Subtask
        List<Subtask> subtasks = new ArrayList<>( collectionSubtask.values());
        return subtasks;
    }

    public List<Epic> getEpics() {                          //Вывод списка Epic
        List<Epic> epics = new ArrayList<>(collectionEpic.values());
        return epics;
    }


    public void clearTask() {                           //Удалление списка Task
        collectionTask.clear();
    }

    public void clearEpic() {                           //Удалление списка Epic
        collectionEpic.clear();
        collectionSubtask.clear();
    }

    public void clearSubtask() {                            //Удалление списка Subtask
        for (int i : collectionSubtask.keySet()) {
            int j = collectionSubtask.get(i).getEpicID();
            List<Integer> subtasksID = collectionEpic.get(j).getSubtasks();
            subtasksID.clear();
            collectionEpic.get(j).setSubtasksID(subtasksID);
            collectionEpic.get(j).setStatus(Status.NEW);
        }
        collectionSubtask.clear();
    }


    public void getTaskID(int id) {                             // поиск по ID
        if (collectionTask.get(id) != null) {
            System.out.println(collectionTask.get(id));
        } else {
            System.out.println("Task c ID " + id + " не найден!");
        }
    }

    public void getEpicID(int id) {
        if (collectionEpic.get(id) != null) {
            System.out.println(collectionEpic.get(id));
        } else {
            System.out.println("Epic c ID " + id + " не найден!");
        }
    }

    public void getSubtaskID(int id) {
        if (collectionSubtask.get(id) != null) {
            System.out.println(collectionSubtask.get(id));
        } else {
            System.out.println("Subtask c ID " + id + " не найден!");
        }
    }

    public void removeTaskID(int id) {                               // удаляем по ID
        if (collectionTask.get(id) != null) {
            collectionTask.remove(id);
        } else {
            System.out.println("Task c ID " + id + " не найден!");
        }
    }

    public void removeEpicID(int id) {
        if (collectionEpic.get(id) != null) {
            List<Integer> subtasksID = collectionEpic.get(id).getSubtasks();
            for (int i : subtasksID) {
                collectionSubtask.remove(i);
            }
            collectionEpic.remove(id);
        } else {
            System.out.println("Epic c ID " + id + " не найден!");
        }
    }

    public void removeSubtaskID(int id) {
        if (collectionSubtask.get(id) != null) {
            int j = collectionSubtask.get(id).getEpicID();
            List<Integer> subtasksID = collectionEpic.get(j).getSubtasks();
            subtasksID.remove((Object) id);
            collectionEpic.get(j).setSubtasksID(subtasksID);
            collectionSubtask.remove(id);
            updateEpicStatus(collectionEpic.get(j));
        } else {
            System.out.println("Subtask c ID " + id + " не найден!");
        }
    }

    public List<Subtask> getEpicSubtask(int id) {        //получение списка подзадач по ID=>Epic
        List<Integer> subtasksID = collectionEpic.get(id).getSubtasks();
        List<Subtask> subtasks = new ArrayList<>();
        for (int i : subtasksID) {
            subtasks.add(collectionSubtask.get(i));
        }
        return subtasks;
    }

    public void updateTask(Task task) {
        for (int i : collectionTask.keySet()) {
            if (collectionTask.get(i).equals(task)) {
                collectionTask.put(i, task);
                collectionTask.get(i).setId(i);
                return;
            } else;
        }
        System.out.println("Task не найдена!");
    }

    public void updateEpic(Epic epic) {
        for (int i : collectionEpic.keySet()){
            if (collectionEpic.get(i).equals(epic)) {
                epic.setId(i);
                epic.setSubtasksID(collectionEpic.get(i).getSubtasks());
                collectionEpic.put(i, epic);
            return;
        } else;
        }System.out.println("Epic не найден!");
    }

    public void updateSubtask(Subtask subtask) {    // обновляет статус
        for (int i : collectionSubtask.keySet()) {
            if (collectionSubtask.get(i).equals(subtask)) {
                subtask.setEpicID(collectionSubtask.get(i).getEpicID());
                subtask.setId(i);
                collectionSubtask.put(i, subtask);
                updateEpicStatus(collectionEpic.get(subtask.getEpicID()));
                return;
            } else;
            }
        System.out.println("Subtask не найдена!");
        }



    public void updateEpicStatus(Epic epic) {
        boolean hasNew = false;
        boolean hasInProgress = false;
        boolean hasDone = false;
        for (int k : epic.getSubtasks()) {
            if (collectionSubtask.get(k).getStatus() == Status.NEW) {
                hasNew = true;
            } else if (collectionSubtask.get(k).getStatus() == Status.IN_PROGRESS) {
                hasInProgress = true;
            } else if (collectionSubtask.get(k).getStatus() == Status.DONE) {
                hasDone = true;

            }

        }
        if (hasNew && !hasInProgress && !hasDone) {
            epic.setStatus(Status.NEW);
        } else if (!hasNew && !hasInProgress && hasDone) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }
}





