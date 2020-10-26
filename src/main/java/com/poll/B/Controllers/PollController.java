package com.poll.B.Controllers;

import com.poll.B.Poll;
import com.poll.B.Repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.poll.B.Vote;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PollController {

    @Autowired
    private PollRepository repository;

    @PostMapping("/polls")
    public Poll savePoll(@RequestBody Poll poll) {
        repository.save(poll);
        return poll;
    }

    @GetMapping("/polls")
    public List<Poll> getAllPolls() {
        return (List<Poll>) repository.findAll();
    }

    @GetMapping("/polls/{id}/votes")
    public List<Vote> putVotes(@PathVariable Long id) {
        return repository.findById(id)
                .map(poll -> {
                    System.out.println(poll.getVotes().size());
                    return poll.getVotes();
                })
                .orElseGet(ArrayList::new);  //TODO: Bad workaround
    }

    @GetMapping("/polls/name/{name}")
    public Poll findByName(@PathVariable String name) {
        return repository.findByName(name);
    }

    @GetMapping("/polls/{id}")
    public Poll findById(@PathVariable long id) {
        return repository.findById(id);
    }

    @DeleteMapping("/polls/{id}")
    public void deleteById(@PathVariable long id) {
        repository.deleteById(id);
    }

    @PutMapping("/polls/{id}")
    public Poll replacePoll(@RequestBody Poll newPoll, @PathVariable Long id) {

        return repository.findById(id)
                .map(poll -> {
                    poll.setName(newPoll.getName());
                    return repository.save(poll);
                })
                .orElseGet(() -> {
                    newPoll.setId(id);
                    return repository.save(newPoll);
                });
    }

    @PutMapping("/polls/{id}/addVote")
    public Vote addVote(@PathVariable Long id, @RequestBody Vote vote) {

        return repository.findById(id)
                .map(poll -> {
                    System.out.println(vote.isYes());
                    poll.addVote(vote);
                    repository.save(poll);
                    return vote;
                })
                .orElseGet(Vote::new);  //TODO: Bad workaround
    }
}
