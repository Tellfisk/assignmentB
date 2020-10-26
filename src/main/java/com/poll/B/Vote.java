package com.poll.B;

import javax.persistence.*;

@Entity
public class Vote {

    boolean yes;

    @ManyToOne
    Person person;

    @ManyToOne
    Poll poll;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean isYes() {
        return yes;
    }

    public void setYes(boolean yes) {
        this.yes = yes;
    }
}
