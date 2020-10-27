package com.poll.B;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Person {
    String name;
    String password;
    boolean isAdmin;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    List<Vote> votes = new ArrayList<>();

    @Id
    @GeneratedValue
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

    public void addVote(Vote vote){
        System.out.println(votes.size());
        votes.add(vote);
    }
}





