package com.example.first.model;

public class personModel {
    private String name,age,email,password,id;

    public personModel(String name, String email, String password, String age) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
    }
    public personModel(String name, String email, String password, String age,String id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.id=id;
    }
    public personModel( String email, String password) {

        this.email = email;
        this.password = password;

    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }
}
