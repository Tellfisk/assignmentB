package com.poll.B;

import javax.persistence.*;

@Entity
public class Person {
    String name;
    String password;
    boolean isAdmin;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Person(){}

    public Person(String name, String password, boolean isAdmin){
        this.name = name;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}





