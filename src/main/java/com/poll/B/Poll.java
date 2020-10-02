package com.poll.B;

import javax.persistence.*;
import java.util.List;

@Entity
public class Poll {
    String name;

    @OneToMany
    List<Vote> votes;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    public Poll(){}

    public Poll(String name){
        this.name = name;
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

    public void addVote(Vote vote){
        votes.add(vote);
    }
}



