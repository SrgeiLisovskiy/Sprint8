package com.company.module;

import com.company.serves.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    private int epicID;


    public Subtask(Integer epicID, String name, String description, Status status) {
        super(name, description, status);
        this.epicID = epicID;
        type = TaskType.SUBTASK;
    }
    public Subtask(Integer epicID, String name, String description, Status status,Duration duration, LocalDateTime startTime) {
        super(name, description, status,duration,startTime);
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

    public Subtask(int id, String name, String description, Status status, Duration duration, LocalDateTime startTime, int epicID) {
        super(id, name, description, status, duration, startTime);
        this.epicID = epicID;
        type = TaskType.SUBTASK;
        endTime = getEndTime();
    }

    public Subtask(String name, String description, Status status, Duration duration, LocalDateTime startTime, int epicID) {
        super(name, description, status, duration, startTime);
        this.epicID = epicID;
        type = TaskType.SUBTASK;
        endTime = getEndTime();
    }
    public Subtask(String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        super(name, description, status, duration, startTime);
        type = TaskType.SUBTASK;
        endTime = getEndTime();
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
                ", status=" + status + ", duration=" + duration +
                ", startTime="+ startTime + ", endTime=" + endTime +
                "}";
    }

}