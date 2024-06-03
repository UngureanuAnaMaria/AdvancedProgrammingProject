package com.example.demo.model;
public class Depot {
    private int id;

    private String name;

    public Depot(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Depot() {
    }


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}