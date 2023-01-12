package com.company.module;

import com.company.serves.TaskType;

public class Subtask extends Task {
    private int epicID;


    public Subtask(Integer epicID, String name, String description, Status status) {
        super(name, description, status);
        this.epicID = epicID;
        type = TaskType.SUBTASK;
    }

    public Subtask(String name, String description,Status status) {
        super(name, description, status);
        type = TaskType.SUBTASK;
    }

    public Subtask(String name, String description, int id, Status status) {
        super(name, description, id, status);
        type = TaskType.SUBTASK;
    }

    public Subtask(String name, String description, int id, Status status, int epicID) {
        super(name, description, id, status);
        this.epicID = epicID;
        type = TaskType.SUBTASK;
    }

    public void setEpicID(Integer epicID) {
        this.epicID = epicID;
    }

    public int getEpicID() {
        return epicID;
    }

    public String toString() {

        return "\n Subtask{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", epicID=" + epicID +
                ", status=" + status;
    }

}