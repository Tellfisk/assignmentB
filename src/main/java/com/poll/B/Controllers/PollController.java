package com.poll.B.Controllers;

import com.poll.B.Poll;
import com.poll.B.Repositories.PollRepository;
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

    @GetMapping("/getAll")
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
}
