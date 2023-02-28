package com.company.serves;

import com.company.module.Epic;
import com.company.module.Status;
import com.company.module.Subtask;
import com.company.module.Task;

import java.util.*;


public class InMemoryTaskManager implements TaskManager {
    private int id = 1;


    HashMap<Integer, Task> collectionTask = new HashMap<>();
    HashMap<Integer, Epic> collectionEpic = new HashMap<>();
   public HashMap<Integer, Subtask> collectionSubtask = new HashMap<>();
    TreeSet<Task> sortedTaskSet = new TreeSet<>((task1, task2) -> {
        if(task1.getStartTime() != null && task2.getStartTime() != null) {
            return task1.getStartTime().compareTo(task2.getStartTime());
        } else if (task1.getStartTime() == null && task2.getStartTime() == null){
            Integer taskID1 = task1.getId();
            Integer taskID2 = task2.getId();
            return taskID1.compareTo((taskID2));
        } else if (task1.getStartTime() == null){
            return 1;
        } else return -1;
    });


    public final HistoryManager historyManager = Managers.getDefaultHistory();


    @Override
    public int addTask(Task task) {                     // добавляем Task
        task.setId(id++);
        collectionTask.put(task.getId(), task);
        updateSortedTaskSet();
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
        List<Integer> subtaskID = epics.getSubtasksID();
        subtaskID.add(subtask.getId());
        updateEpicStatus(epics);
        updateSortedTaskSet();
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
        List<Epic> epics = new ArrayList<>();
        for (int i: collectionEpic.keySet()){
           Epic epic = collectionEpic.get(i);
           epics.add(epic);
        }
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
            List<Integer> subtasksID = epic.getSubtasksID();
            subtasksID.clear();
            epic.setSubtasksID(subtasksID);
            updateEpicStatus(epic);
            updateSortedTaskSet();

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
            updateSortedTaskSet();

        } else {
            System.out.println("Task c ID " + id + " не найден!");
        }
    }

    @Override
    public void removeEpicID(int id) {
        if (collectionEpic.get(id) != null) {
            List<Integer> subtasksID = collectionEpic.get(id).getSubtasksID();
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
            List<Integer> subtasksID = epic.getSubtasksID();
            subtasksID.remove((Object) id);
            epic.setSubtasksID(subtasksID);
            collectionSubtask.remove(id);
            historyManager.remove(id);
            updateEpicStatus(epic);
            updateSortedTaskSet();

        } else {
            System.out.println("Subtask c ID " + id + " не найден!");
        }
    }


    @Override
    public void updateTask(Task task) {
        if (collectionTask.get(task.getId()) != null) {
            collectionTask.put(task.getId(), task);
            updateSortedTaskSet();

        } else {
            System.out.println("Task не найдена!");
        }
    }


    @Override
    public void updateEpic(Epic epic) {
        if (collectionEpic.get(epic.getId()) != null) {
            epic.setSubtasksID(collectionEpic.get(epic.getId()).getSubtasksID());
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
            updateSortedTaskSet();

        } else {
            System.out.println("Subtask не найдена!");
        }
    }


    private void updateEpicStatus(Epic epic) {
        boolean hasNew = false;
        boolean hasInProgress = false;
        boolean hasDone = false;
        for (int k : epic.getSubtasksID()) {
            Subtask subtask = collectionSubtask.get(k);
            if (subtask.getStatus() == Status.NEW) {
                hasNew = true;
            } else if (subtask.getStatus() == Status.IN_PROGRESS) {
                hasInProgress = true;
            } else if (subtask.getStatus() == Status.DONE) {
                hasDone = true;

            }

        }
        if (hasNew && !hasInProgress && !hasDone || epic.getSubtasksID().size() == 0) {
            epic.setStatus(Status.NEW);
        } else if (!hasNew && !hasInProgress && hasDone) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }
    public HashMap<Integer, Subtask> getSubtask(){
        return collectionSubtask;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    public HistoryManager getHistoryManager() {
        return historyManager;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        return sortedTaskSet;
    }

    @Override
    public List<Subtask> getEpicSubtask(int id) {
        if (collectionEpic.containsKey(id)) {
            List<Subtask> subtasksIDNew = new ArrayList<>();
            Epic epic = collectionEpic.get(id);
            for (int i = 0; i < epic.getSubtasksID().size(); i++) {
                subtasksIDNew.add(collectionSubtask.get(epic.getSubtasksID().get(i)));
            }
            return subtasksIDNew;
        } else {
            return Collections.emptyList();
        }
    }

    public TreeSet<Task> updateSortedTaskSet(){
        sortedTaskSet.clear();
       sortedTaskSet.addAll(getTasks());
       sortedTaskSet.addAll(getSubtasks());
        return sortedTaskSet ;
    }
}





