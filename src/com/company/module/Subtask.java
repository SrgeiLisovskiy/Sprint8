package com.company.module;

public class Subtask extends Task {
    private int epicID;

    public Subtask(String name, String description, Status status) {
        super(name, description, status);
    }

    public void setEpicID(int epicID) {
        this.epicID = epicID;
    }

    public int getEpicID() {
        return epicID;
    }

    public Status getStatus() {
        return this.status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {

        return "\n Subtask{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status;
    }
    public void setStatus(Status status){
        this.status = status;

    }
    public boolean equals(Subtask subtask){
        if(!subtask.getName().equals(this.getName())){
            return false;
        }
        if (!subtask.getDescription().equals(this.getDescription())){
            return false;
        }
        if (subtask.getEpicID() != this.getEpicID()){
            return false;
        }
        return true;
    }
}