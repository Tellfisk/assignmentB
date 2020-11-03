package com.poll.B;

import javax.persistence.*;

@Entity
public class Vote {

    private boolean yes;
    private long fkpoll;
    private long fkperson;

    //@ManyToOne
    //@JoinColumn
    //private Person person;

    @ManyToOne
    @JoinColumn
    private Poll poll;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Vote(){}

    public Vote(boolean yes/*, Person person*/, Poll poll) {
        this.yes = yes;
//        this.person = person;
        this.poll = poll;
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

 /*   public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }*/

    public boolean isYes() {
        return yes;
    }

    public void setYes(boolean yes) {
        this.yes = yes;
    }

    public long getFkpoll() {
        return fkpoll;
    }

    public void setFkpoll(long fkpoll) {
        this.fkpoll = fkpoll;
    }

    public long getFkperson() {
        return fkperson;
    }

    public void setFkperson(long fkperson) {
        this.fkperson = fkperson;
    }
}
