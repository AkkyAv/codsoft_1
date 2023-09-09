package com.example.to_do;

public class TaskItem {
    private String title;
    private String description;
    private String priority;
    private boolean completed;

    public TaskItem(String title, String description, String priority, boolean completed) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.completed = completed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void toggleCompleted() {
        completed = !completed;
    }

    public String serializeToString() {
        return title + "|" + description + "|" + priority + "|" + completed;
    }
    public static TaskItem deserializeFromString(String serializedTask) {
        String[] parts = serializedTask.split("\\|");
        if (parts.length == 4) {
            return new TaskItem(parts[0], parts[1], parts[2], Boolean.parseBoolean(parts[3]));
        }
        return null;
    }
}
