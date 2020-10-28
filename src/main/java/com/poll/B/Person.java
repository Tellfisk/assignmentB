package com.poll.B;

import javax.persistence.*;
import java.util.List;

@Entity
public class Person {
    private String name;
    private String password;
    private boolean isAdmin;

    @OneToMany(mappedBy = "person")
    private List<Vote> votes;

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

    public void setVotes(List<Vote> votes){
        votes.addAll(this.votes);
        this.votes = votes;
    }

    public List<Vote> getVotes() {
        return votes;
    }
}





