package com.company.module;

import com.company.serves.TaskType;

import java.util.List;

public class Epic extends Task {

    private List<Integer> subtasksID;

    public Epic(String name, String description) {
        super(name, description);
        type = TaskType.EPIC;
    }

    public Epic(String name, String description, int id) {
        super(name, description, id);
        type = TaskType.EPIC;
    }

    public List<Integer> getSubtasks() {  // список подзадач
        return this.subtasksID;
    }
    public void setSubtasksID(List<Integer> subtasksID) {
        this.subtasksID = subtasksID;
    }

    @Override
    public String toString() {
        return "\n Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status + ", subtasks=" + subtasksID + "}";
    }
}
