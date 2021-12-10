package com.example.todolist_example.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks_table")
public class Task {

    @PrimaryKey(autoGenerate = true)
    int id;
    private String taskHeader;
    private String taskDescription;

    public Task(String taskHeader, String taskDescription) {
        this.taskHeader = taskHeader;
        this.taskDescription = taskDescription;
    }

    public void setTaskHeader(String taskHeader) {
        this.taskHeader = taskHeader;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskHeader() {
        return taskHeader;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskHeader='" + taskHeader + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                '}';
    }
}
