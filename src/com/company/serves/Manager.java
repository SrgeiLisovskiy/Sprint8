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
    private int id;


    public HashMap<Integer, Task> collectionTask = new HashMap<>();
    public HashMap<Integer, Epic> collectionEpic = new HashMap<>();
    public HashMap<Integer, Subtask> collectionSubtask = new HashMap<>();


    public void addTask(Task task) {                     // добавляем задачи
        task.setId(id++);
        collectionTask.put(task.getId(), task);

    }

    public void addEpic(Epic epic) {
        epic.setId(id++);
        List<Integer> epicSubtaskID = epic.getSubtasks();
        for (int k : epicSubtaskID) {
            collectionSubtask.get(k).setEpicID(epic.getId());
        }
        collectionEpic.put(epic.getId(), epic);
        updateEpicStatus(epic);
    }

    public void addSubtask(Subtask subtask) {
        subtask.setId(id++);
        collectionSubtask.put(subtask.getId(), subtask);

    }


    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.addAll((Collection<? extends Task>) collectionTask);
        return tasks;
    }

    public List<Subtask> getSubtasks() {
        List<Subtask> subtasks = new ArrayList<>();
        subtasks.addAll((Collection<? extends Subtask>) collectionSubtask);
        return subtasks;
    }

    public List<Epic> getEpics() {
        List<Epic> epic = new ArrayList<>();
        epic.addAll((Collection<? extends Epic>) collectionEpic);
        return epic;
    }

    public void clearAllTask() {         //Удаляем все задачи
        collectionEpic.clear();
        collectionTask.clear();
        collectionSubtask.clear();
    }

    public Task getByID(int id) {            // поиск по ID
        if (collectionTask.get(id) != null) {
            return collectionTask.get(id);
        } else if (collectionEpic.get(id) != null) {
            return collectionEpic.get(id);
        } else if (collectionSubtask.get(id) != null) {
            return collectionSubtask.get(id);
        }
        return null;
    }

    public void removeID(int id) {               // удаляем по ID
        if (collectionTask.get(id) != null) {
            collectionTask.remove(id);
        } else if (collectionEpic.get(id) != null) {
            collectionEpic.remove(id);
        } else if (collectionSubtask.get(id) != null) {
            List<Integer> subtasksID = collectionEpic.get(collectionSubtask.get(id).getEpicID()).getSubtasks();
            subtasksID.remove((Object) id);
            collectionEpic.get(collectionSubtask.get(id).getEpicID()).setSubtasksID(subtasksID);
            updateEpicStatus(collectionEpic.get(collectionSubtask.get(id).getEpicID()));
            collectionSubtask.remove(id);

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
        collectionTask.put(task.getId(),task);
    }

    public void updateEpic(Epic epic) {
        collectionEpic.put(epic.getId(), epic);
    }

    public void updateSubtask(Subtask subtask) {    // обновляет статус
        collectionSubtask.forEach((k, v) -> {
            if (v.equals(subtask)) {
               v.setStatus(subtask.getStatus());
            }
        });
        updateEpicStatus(collectionEpic.get(subtask.getEpicID()));
    }

    public  void updateEpicStatus(Epic epic) {
        boolean hasNew = false;
        boolean hasInProgress = false;
        boolean hasDone = false;
        List<Integer> subtaskID = epic.getSubtasks();
        for (int k : subtaskID) {
            if (collectionSubtask.get(k).getStatus() == Status.NEW) {
                hasNew = true;
            } else if (collectionSubtask.get(k).getStatus() == Status.IN_PROGRESS) {
                hasInProgress = true;
            } else if (collectionSubtask.get(k).getStatus() == Status.DONE) {
                hasDone = true;

            }

        }
        if (hasNew && !hasInProgress && !hasDone){
            epic.setStatus(Status.NEW);
        } else if (!hasNew && !hasInProgress && hasDone) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }
}




