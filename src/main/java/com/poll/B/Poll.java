package com.poll.B;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Poll {
    private String name;
    private String creator;
    private long fkperson;

    @OneToMany(mappedBy = "poll")
    public List<Vote> votes = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public void setVotes(List<Vote> votes) {
        votes.addAll(this.votes);
        this.votes = votes;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public long getFkperson() {
        return fkperson;
    }

    public void setFkperson(long fkperson) {
        this.fkperson = fkperson;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}



