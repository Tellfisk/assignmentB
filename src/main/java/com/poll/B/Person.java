package com.poll.B;

import javax.persistence.*;

@Entity
public class Person {
    private String email;
    private String password;
    private boolean isAdmin;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Person(){}

    public Person(String email, String password, boolean isAdmin){
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

/*    public void setVotes(List<Vote> votes){
        votes.addAll(this.votes);
        this.votes = votes;
    }

    public List<Vote> getVotes() {
        return votes;
    }*/
}





