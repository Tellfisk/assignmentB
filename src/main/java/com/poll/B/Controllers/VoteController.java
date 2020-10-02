package com.poll.B.Controllers;

import com.poll.B.Repositories.VoteRepository;
import com.poll.B.User;
import com.poll.B.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VoteController {

    @Autowired
    private VoteRepository repository;

    @PostMapping("/savePoll")
    public String saveVote(@RequestBody Vote vote) {
        repository.save(vote);
        return "vote saved";
    }

    @GetMapping("/getAll")
    public List<Vote> getAllVotes() {
        return (List<Vote>) repository.findAll();
    }

    @GetMapping("/geVoteByName/{user}")
    public Vote findByUser(@PathVariable User user) {
        return repository.findByUser(user);
    }

    @GetMapping("/getVoteById/{id}")
    public Vote findById(@PathVariable long id) {
        return repository.findById(id);
    }
}
