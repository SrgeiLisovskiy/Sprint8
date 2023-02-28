package com.company.module;

import com.company.serves.InMemoryTaskManager;
import com.company.serves.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
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

    public Epic(int id, String name, String description, Status status, Duration duration, LocalDateTime startTime, List<Integer> subtasksID) {
        super(id, name, description, status, duration, startTime);
        this.subtasksID = subtasksID;
        type = TaskType.EPIC;

    }

    public Epic(String name, String description, Status status, Duration duration, LocalDateTime startTime, List<Integer> subtasksID) {
        super(name, description, status, duration, startTime);
        this.subtasksID = subtasksID;
        type = TaskType.EPIC;
    }
    public Epic(String name, String description, Duration duration, LocalDateTime startTime) {
        super(name, description, duration, startTime);
        type = TaskType.EPIC;
    }

    public List<Integer> getSubtasksID() {  // список подзадач
        return this.subtasksID;
    }

    public void setSubtasksID(List<Integer> subtasksID) {
        this.subtasksID = subtasksID;
    }

    private void updateDates() {
        InMemoryTaskManager manager = new InMemoryTaskManager();
        HashMap<Integer, Subtask> subtasks = manager.getSubtask();
        Duration sumDuration = null;
        LocalDateTime newStartTime = null;
        LocalDateTime newEndTime = null;
        if (subtasksID != null) {
            for (int id : subtasksID) {
                Subtask subtask = subtasks.get(id);
                if (subtask.getStartTime() != null && subtask.getDuration() != null) {
                    if (newStartTime == null || newStartTime.isAfter(subtask.getStartTime()))
                        newStartTime = subtask.getStartTime();
                    if (newEndTime == null || newEndTime.isBefore(subtask.getEndTime()))
                        newEndTime = subtask.getEndTime();
                    if (sumDuration == null)
                        sumDuration = subtask.getDuration();
                    else
                        sumDuration = sumDuration.plus(subtask.getDuration());
                }
            }
        }
        duration = sumDuration;
        startTime = newStartTime;
        endTime = newEndTime;
    }


    @Override
    public String toString() {
        return "\n Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status + ", duration=" + duration +
                ", startTime="+ startTime + ", endTime=" + endTime
                +", subtasks=" + subtasksID + "}";
    }


}
