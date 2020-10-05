package com.poll.B.Controllers;

import com.poll.B.User;
import com.poll.B.Vote;
import com.poll.B.Repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VoteController {

    @Autowired
    private VoteRepository repository;

    @PostMapping("/saveVote")
    public String saveVote(@RequestBody Vote vote) {
        repository.save(vote);
        return "vote saved";
    }

    @GetMapping("/getAllVotes")
    public List<Vote> getAllVotes() {
        return (List<Vote>) repository.findAll();
    }

    @GetMapping("/geVoteByUser/{user}")
    public Vote findByUser(@PathVariable User user) {
        return repository.findByUser(user);
    }

    @GetMapping("/getVoteById/{id}")
    public Vote findById(@PathVariable long id) {
        return repository.findById(id);
    }

    @DeleteMapping
    public void deleteById(@PathVariable long id) {
        repository.deleteById(id);
    }

    @PutMapping("/vote/{id}")
    public Vote replaceVote(@RequestBody Vote newVote, @PathVariable Long id) {

        return repository.findById(id)
                .map(vote -> {
                    vote.setYes(newVote.isYes());
                    vote.setUser(newVote.getUser());
                    return repository.save(vote);
                })
                .orElseGet(() -> {
                    newVote.setId(id);
                    return repository.save(newVote);
                });
    }
}


