package com.company.module;

import java.util.Objects;

public class Task {
    String name;
    String description;
    int id;
    Status status;


    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;

    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }


    @Override
    public String toString() {
        return "\n Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return  Objects.equals(name, task.name)
                && Objects.equals(description, task.description) ;
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

}

