package com.poll.B.Controllers;

import com.poll.B.Person;
import com.poll.B.Poll;
import com.poll.B.Vote;
import com.poll.B.Repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
public class VoteController {

    //@Autowired
    private VoteRepository voteRepository;

    @PostMapping("/votes")
    public Vote saveVote(@RequestBody Vote vote) {
        voteRepository.save(vote);
        return vote;
    }

    @GetMapping("/votes")
    public List<Vote> getAllVotes() {
        return (List<Vote>) voteRepository.findAll();
    }

    @GetMapping("/votes/person/{person}")
    public Vote findByPerson(@PathVariable Person person) {
        return voteRepository.findByPerson(person);
    }

    @GetMapping("/votes/poll/{poll}")
    public Vote findByPoll(@PathVariable Poll poll) {
        return voteRepository.findByPoll(poll);
    }

    @GetMapping("/votes/{id}")
    public Vote findById(@PathVariable long id) {
        return voteRepository.findById(id);
    }

    @DeleteMapping("/votes/{id}")
    public void deleteById(@PathVariable long id) {
        voteRepository.deleteById(id);
    }

    @PutMapping("/votes/{id}")
    public Vote replaceVote(@RequestBody Vote newVote, @PathVariable Long id) {

        return voteRepository.findById(id)
                .map(vote -> {
                    vote.setYes(newVote.isYes());
                    vote.setPerson(newVote.getPerson());
                    vote.setPoll(newVote.getPoll());
                    return voteRepository.save(vote);
                })
                .orElseGet(() -> {
                    newVote.setId(id);
                    return voteRepository.save(newVote);
                });
    }
}
