package com.poll.B;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Poll {
    String name;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL)
    List<Vote> votes = new ArrayList<>();

    @Id
    @GeneratedValue
    //@Column(name = "POLL_ID", updatable = false, nullable = false)
    private Long id;

    public Poll(){}

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
        System.out.println(votes.size());
        votes.add(vote);
    }

    public List<Vote> getVotes() {
        return votes;
    }
}



