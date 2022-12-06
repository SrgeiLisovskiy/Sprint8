package com.company.module;

public class Subtask extends Task {
    private Integer epicID;

    public Subtask(Integer epicID, String name, String description, Status status) {
        super(name, description, status);
        this.epicID = epicID;
    }

    public Subtask(String name, String description,Status status) {
        super(name, description, status);
    }

    public void setEpicID(Integer epicID) {
        this.epicID = epicID;
    }

    public int getEpicID() {
        return epicID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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