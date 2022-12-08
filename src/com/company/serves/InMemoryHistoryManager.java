package com.company.serves;

import com.company.module.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private final  int SIZE_HISTORY = 10;
    private List<Task> taskHistory = new ArrayList();
    @Override
    public List<Task> getHistory() {
        return taskHistory;
    }

    @Override
    public void add(Task task) {
        taskHistory.add(task);
        if(taskHistory.size() > SIZE_HISTORY){
            taskHistory.remove(0);
        }else;
    }


}
