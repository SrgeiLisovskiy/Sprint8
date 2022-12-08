package com.company.serves;

import com.company.module.Epic;
import com.company.module.Subtask;
import com.company.module.Task;

import java.util.List;

public interface TaskManager {
    int addTask(Task task);

    int addEpic(Epic epic);

    int addSubtask(Subtask subtask);

    List<Task> getTasks();

    List<Subtask> getSubtasks();

    List<Epic> getEpics();

    void clearTask();

    void clearEpic();

    void clearSubtask();

    Task getTaskID(int id);

    Epic getEpicID(int id);

    Subtask getSubtaskID(int id);

    void removeTaskID(int id);

    void removeEpicID(int id);

    void removeSubtaskID(int id);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    List<Task> getHistory();

}
