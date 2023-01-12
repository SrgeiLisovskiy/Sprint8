package com.company.serves;

import com.company.module.Epic;
import com.company.module.Subtask;
import com.company.module.Task;

import java.io.IOException;
import java.util.List;

public interface TaskManager {
    int addTask(Task task) throws IOException;

    int addEpic(Epic epic) throws IOException;

    int addSubtask(Subtask subtask) throws IOException;

    List<Task> getTasks();

    List<Subtask> getSubtasks();

    List<Epic> getEpics();

    void clearTask() throws IOException;

    void clearEpic() throws IOException;

    void clearSubtask() throws IOException;

    Task getTaskID(int id) throws IOException;

    Epic getEpicID(int id) throws IOException;

    Subtask getSubtaskID(int id) throws IOException;

    void removeTaskID(int id);

    void removeEpicID(int id);

    void removeSubtaskID(int id);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    List<Task> getHistory();

}
