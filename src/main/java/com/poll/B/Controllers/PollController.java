package com.poll.B.Controllers;

import com.poll.B.Poll;
import com.poll.B.Repositories.PollRepository;
import com.poll.B.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public String addVote(@PathVariable Long id, @RequestBody Vote vote) {
        Optional<Poll> tempPoll;
        tempPoll = repository.findById(id);
        System.out.println(tempPoll);
        return tempPoll.map(poll -> {
            System.out.println(vote.isYes());
            VoteController.saveVote();
            poll.addVote(vote);
            return "fart men foreal";
        })
                .orElseGet(() -> {return "fart";});

        /*if(tempPoll.isPresent()){
            tempPoll.addVote(vote);
            return vote.toString();
        }
        else{
            return "fart";
        }*/

    }


}
