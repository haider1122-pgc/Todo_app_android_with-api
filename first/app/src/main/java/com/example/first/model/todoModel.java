package com.example.first.model;

import java.util.concurrent.atomic.AtomicInteger;

public class todoModel {

    private String title,description;
    private String time;
    private boolean completed;
    private String  id;

    public todoModel(String title, String descriptipn, String time, boolean status,String id) {
        this.title = title;
        this.description = descriptipn;
        this.time = time;
        this.completed = status;
        this.id=id;


    }
    public todoModel(String title, String descriptipn, String time, boolean status) {
        this.title = title;
        this.description = descriptipn;
        this.time = time;
        this.completed = status;



    }
    public todoModel( boolean completed) {
        this.completed = completed;
    }
    public todoModel(String title, String descriptipn, String time) {
        this.title = title;
        this.description = descriptipn;
        this.time = time;


    }
    public todoModel(String description) {
        this.description = description;

    }

    public void setStatus(boolean status) {
        this.completed = status;
    }

    public todoModel(){

    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getDescriptipn() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public boolean isStatus() {
        return completed;
    }
    
}
