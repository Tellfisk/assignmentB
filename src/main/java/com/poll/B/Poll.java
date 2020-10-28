package com.poll.B;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Poll {
    private String name;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    public List<Vote> votes = new ArrayList<>();

    @Id
    @GeneratedValue
    private Long id;

    public Poll(){}

    public Poll(String name, List<Vote> votes) {
        this.name = name;
        this.votes = votes;
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

    public void setVotes(List<Vote> votes){
        System.out.println("Votelist size inside Poll class " + this.votes.size());
        votes.addAll(this.votes);
        this.votes = votes;
    }

    public List<Vote> getVotes() {
        return votes;
    }
}



