package com.company.serves;

import com.company.module.Epic;
import com.company.module.Status;
import com.company.module.Subtask;
import com.company.module.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InMemoryTaskManager implements TaskManager {
    private int id = 1;


    public HashMap<Integer, Task> collectionTask = new HashMap<>();
    public HashMap<Integer, Epic> collectionEpic = new HashMap<>();
    public HashMap<Integer, Subtask> collectionSubtask = new HashMap<>();


    private final HistoryManager historyManager = Managers.getDefaultHistory();


    @Override
    public int addTask(Task task) {                     // добавляем Task
        task.setId(id++);
        collectionTask.put(task.getId(), task);
        return task.getId();
    }

    @Override
    public int addEpic(Epic epic) {                                //Добавляем Epic
        List<Integer> subtaskID = new ArrayList<>();
        epic.setSubtasksID(subtaskID);
        epic.setId(id++);
        epic.setStatus(Status.NEW);
        collectionEpic.put(epic.getId(), epic);
        return epic.getId();
    }

    @Override
    public int addSubtask(Subtask subtask) {                       //Добавляем Subtask
        subtask.setId(id++);
        collectionSubtask.put(subtask.getId(), subtask);
        Epic epics = collectionEpic.get(subtask.getEpicID());
        List<Integer> subtaskID = epics.getSubtasks();
        subtaskID.add(subtask.getId());
        updateEpicStatus(epics);
        return subtask.getId();
    }


    @Override
    public List<Task> getTasks() {                              //Вывод списка Task
        List<Task> tasks = new ArrayList<>(collectionTask.values());
        return tasks;
    }

    @Override
    public List<Subtask> getSubtasks() {                        //Вывод списка Subtask
        List<Subtask> subtasks = new ArrayList<>(collectionSubtask.values());
        return subtasks;
    }

    @Override
    public List<Epic> getEpics() {                          //Вывод списка Epic
        List<Epic> epics = new ArrayList<>(collectionEpic.values());
        return epics;
    }


    @Override
    public void clearTask() {                           //Удалление списка Task
        collectionTask.clear();
    }

    @Override
    public void clearEpic() {                           //Удалление списка Epic
        collectionEpic.clear();
        collectionSubtask.clear();
    }

    @Override
    public void clearSubtask() {                            //Удалление списка Subtask
        collectionSubtask.clear();
        for (int i : collectionEpic.keySet()) {
            Epic epic = collectionEpic.get(i);
            List<Integer> subtasksID = epic.getSubtasks();
            subtasksID.clear();
            epic.setSubtasksID(subtasksID);
            updateEpicStatus(epic);
        }

    }


    @Override
    public Task getTaskID(int id) {                             // поиск по ID
        if (collectionTask.get(id) != null) {
            Task task = collectionTask.get(id);
            historyManager.add(task);
            return task;

        } else {
            return null;
        }
    }

    @Override
    public Epic getEpicID(int id) {
        if (collectionEpic.get(id) != null) {
            Epic epic = collectionEpic.get(id);
            historyManager.add(epic);
            return epic;
        } else {
            return null;
        }
    }

    @Override
    public Subtask getSubtaskID(int id) {
        if (collectionSubtask.get(id) != null) {
            Subtask subtask = collectionSubtask.get(id);
            historyManager.add(subtask);
            return subtask;
        } else {
            return null;
        }
    }

    @Override
    public void removeTaskID(int id) {                               // удаляем по ID
        if (collectionTask.get(id) != null) {
            collectionTask.remove(id);
            historyManager.remove(id);
        } else {
            System.out.println("Task c ID " + id + " не найден!");
        }
    }

    @Override
    public void removeEpicID(int id) {
        if (collectionEpic.get(id) != null) {
            List<Integer> subtasksID = collectionEpic.get(id).getSubtasks();
            for (int i : subtasksID) {
                collectionSubtask.remove(i);
                historyManager.remove(i);
            }
            collectionEpic.remove(id);
            historyManager.remove(id);
        } else {
            System.out.println("Epic c ID " + id + " не найден!");
        }
    }

    @Override
    public void removeSubtaskID(int id) {
        if (collectionSubtask.get(id) != null) {
            int j = collectionSubtask.get(id).getEpicID();
            Epic epic = collectionEpic.get(j);
            List<Integer> subtasksID = epic.getSubtasks();
            subtasksID.remove((Object) id);
            epic.setSubtasksID(subtasksID);
            collectionSubtask.remove(id);
            historyManager.remove(id);
            updateEpicStatus(epic);
        } else {
            System.out.println("Subtask c ID " + id + " не найден!");
        }
    }

    private List<Subtask> getEpicSubtask(int id) {        //получение списка подзадач по ID=>Epic
        List<Integer> subtasksID = collectionEpic.get(id).getSubtasks();
        List<Subtask> subtasks = new ArrayList<>();
        for (int i : subtasksID) {
            subtasks.add(collectionSubtask.get(i));
        }
        return subtasks;
    }

    @Override
    public void updateTask(Task task) {
        if (collectionTask.get(task.getId()) != null) {
            collectionTask.put(task.getId(), task);

        } else {
            System.out.println("Task не найдена!");
        }
    }


    @Override
    public void updateEpic(Epic epic) {
        if (collectionEpic.get(epic.getId()) != null) {
            epic.setSubtasksID(collectionEpic.get(epic.getId()).getSubtasks());
            collectionEpic.put(epic.getId(), epic);
        } else {
            System.out.println("Epic не найден!");
        }
    }


    @Override
    public void updateSubtask(Subtask subtask) {    // обновляет статус
        Subtask subtasks = collectionSubtask.get(subtask.getId());
        if (subtasks != null) {
            subtasks.setName(subtask.getName());
            subtasks.setDescription(subtask.getDescription());
            subtasks.setStatus(subtask.getStatus());
            updateEpicStatus(collectionEpic.get(subtasks.getEpicID()));
        } else {
            System.out.println("Subtask не найдена!");
        }
    }


    private void updateEpicStatus(Epic epic) {
        boolean hasNew = false;
        boolean hasInProgress = false;
        boolean hasDone = false;
        for (int k : epic.getSubtasks()) {
            Subtask subtask = collectionSubtask.get(k);
            if (subtask.getStatus() == Status.NEW) {
                hasNew = true;
            } else if (subtask.getStatus() == Status.IN_PROGRESS) {
                hasInProgress = true;
            } else if (subtask.getStatus() == Status.DONE) {
                hasDone = true;

            }

        }
        if (hasNew && !hasInProgress && !hasDone || epic.getSubtasks().size() == 0) {
            epic.setStatus(Status.NEW);
        } else if (!hasNew && !hasInProgress && hasDone) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}





