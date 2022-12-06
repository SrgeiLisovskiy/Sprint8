package com.company.module;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subtasksID;

    public Epic(String name, String description) {
        super(name, description);
    }

    public void setStatus(Status status) {
        this.status = status;

    }

    public List<Integer> getSubtasks() {  // список подзадач
        return this.subtasksID;
    }

    public void setSubtasksID(List<Integer> subtasksID) {
        this.subtasksID = subtasksID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
