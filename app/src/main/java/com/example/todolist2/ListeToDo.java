package com.example.todolist2;

public class ListeToDo {

    String title_task, statut_task, date_task, note_task, key_task, user_id;

    public ListeToDo() {
    }

    public ListeToDo(String title_task, String statut_task, String date_task, String note_task, String key_task, String user_id) {
        this.title_task = title_task;
        this.statut_task = statut_task;
        this.date_task = date_task;
        this.note_task = note_task;
        this.key_task = key_task;
        this.user_id = user_id;
    }

    public String getKey_task() {
        return key_task;
    }

    public void setKey_task(String key_task) {
        this.key_task = key_task;
    }

    public String getTitle_task() {
        return title_task;
    }

    public void setTitle_task(String title_task) {
        this.title_task = title_task;
    }

    public String getStatut_task() {
        return statut_task;
    }

    public void setStatut_task(String statut_task) {
        this.statut_task = statut_task;
    }

    public String getDate_task() {
        return date_task;
    }

    public void setDate_task(String date_task) {
        this.date_task = date_task;
    }

    public String getNote_task() {
        return note_task;
    }

    public void setNote_task(String note_task) {
        this.note_task = note_task;
    }

    public String getUser_id() { return user_id; }

    public void setUser_id(String user_id) { this.user_id = user_id; }
}
