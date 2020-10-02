package com.poll.B;

import javax.persistence.*;

@Entity
public class Vote {
    @OneToOne
    User user;
    boolean yes;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    public Vote(){}

    public Vote(boolean yes){
        this.yes = yes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isYes() {
        return yes;
    }

    public void setYes(boolean yes) {
        this.yes = yes;
    }
}



