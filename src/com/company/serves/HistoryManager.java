package com.company.serves;

import com.company.module.Task;

import java.util.List;

public interface HistoryManager {

    List<Task> getHistory();          // Получение списка просмотренных задач

    void remove(int id);
     void add(Task task);         //Дабавление задачи в список

}
