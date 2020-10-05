package com.poll.B.Controllers;

import com.poll.B.Poll;
import com.poll.B.Repositories.PollRepository;
import com.poll.B.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PollController {

    @Autowired
    private PollRepository repository;

    @PostMapping("/savePoll")
    public String savePoll(@RequestBody Poll poll) {
        repository.save(poll);
        return "poll saved";
    }

    @GetMapping("/getAllPolls")
    public List<Poll> getAllPolls() {
        return (List<Poll>) repository.findAll();
    }

    @GetMapping("/getPollByName/{name}")
    public Poll findByName(@PathVariable String name) {
        return repository.findByName(name);
    }

    @GetMapping("/getPollById/{id}")
    public Poll findById(@PathVariable long id) {
        return repository.findById(id);
    }

    @DeleteMapping("/deletePoll/{id}")
    public void deleteById(@PathVariable long id) {
        repository.deleteById(id);
    }

    @PutMapping("/poll/{id}")
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
}
