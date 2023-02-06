package com.company.module;

import com.company.serves.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    String name;
    String description;
    int id;
    Status status;

    TaskType type;
    Duration duration;
    LocalDateTime startTime;
    LocalDateTime endTime;


    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
        type = TaskType.TASK;

    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        type = TaskType.TASK;
    }

    public Task(String name, String description, int id, Status status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        type = TaskType.TASK;
    }

    public Task(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
        type = TaskType.TASK;
    }

    public Task(int id, String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = getEndTime();
        type = TaskType.TASK;
    }

    public Task(String name, String description, Status status, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = getEndTime();
        type = TaskType.TASK;
    }
    public Task(String name, String description, Duration duration, LocalDateTime startTime) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.startTime = startTime;
        this.endTime = getEndTime();
        type = TaskType.TASK;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "\n Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status + ", duration=" + duration +
                ", startTime="+ startTime + ", endTime=" + endTime
                +
                '}';
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }
    public LocalDateTime getEndTime() {
        if (startTime != null && duration != null)
            return startTime.plusSeconds(duration.toSeconds());
        else
            return null;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}

