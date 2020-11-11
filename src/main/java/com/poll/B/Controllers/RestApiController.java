package com.poll.B.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poll.B.*;
import com.poll.B.Exceptions.PersonNotFoundException;
import com.poll.B.Messages.Producer;
import com.poll.B.Repositories.PersonRepository;
import com.poll.B.Repositories.PollRepository;
import com.poll.B.Repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

@RestController
public class RestApiController {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private PollRepository pollRepository;

    @PostMapping("/persons")
    public Person savePerson(@RequestBody Person person) {
        personRepository.save(person);
        return person;
    }

    @GetMapping("/persons")
    public List<Person> getAllPersons() {
        return (List<Person>) personRepository.findAll();
    }

    @GetMapping("/persons/{id}")
    public Person findById(@PathVariable Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    @GetMapping("/persons/email/{email}")
    public Person findPersonByEmail(@PathVariable String email) {
        Person person = personRepository.findByEmail(email);
        Producer producer = new Producer(person);
        return person;
    }

    @GetMapping("/persons/{id}/votes")
    public List<Vote> getVotesFromPerson(@PathVariable Long id) {
        return personRepository.findById(id)
                .map(person -> voteRepository.findAllByFkperson(person.getId()))
                .orElseGet(ArrayList::new);  //TODO: Bad workaround
    }

    @GetMapping("/persons/{id}/polls")
    public List<Poll> getPollsFromPerson(@PathVariable Long id) {
        return personRepository.findById(id)
                .map(person -> pollRepository.findAllByFkperson(person.getId()))
                .orElseGet(ArrayList::new);  //TODO: Bad workaround
    }

    @PutMapping("/persons/{id}")
    public Person replaceUser(@RequestBody Person newPerson, @PathVariable Long id) {
        return personRepository.findById(id)
                .map(person -> {
                    person.setEmail(newPerson.getEmail());
                    return personRepository.save(person);
                })
                .orElseGet(() -> {
                    newPerson.setId(id);
                    return personRepository.save(newPerson);
                });
    }

    @DeleteMapping("/persons/{id}")
    public void deletePersonById(@PathVariable long id) {
        personRepository.deleteById(id);
    }

    @PostMapping("/polls")
    public Poll savePoll(@RequestBody Poll poll) {
        pollRepository.save(poll);
        new Producer(poll);
        return poll;
    }

    @GetMapping("/polls")
    //@CrossOrigin(origins = "http://localhost:8080")
    public List<Poll> getAllPolls() {
        List<Poll> polls = (List<Poll>) pollRepository.findAll();
        for (Poll poll : polls) {
            List<Vote> votes = (List<Vote>) voteRepository.findAllByFkpoll(poll.getId());
            poll.setVotes(votes);
        }
        return polls;
    }

    @GetMapping("/polls/{id}/votes")
    public List<Vote> getVotesFromPoll(@PathVariable Long id) {
        return pollRepository.findById(id)
                .map(poll -> voteRepository.findAllByFkpoll(poll.getId()))
                .orElseGet(ArrayList::new);  //TODO: Bad workaround
    }

    @GetMapping("/polls/{id}/distribution")
    public ResponseEntity<VoteDistribution> getDistributionFromPoll(@PathVariable Long id) {
        return pollRepository.findById(id)
                .map(poll -> {
                    List<Vote> votes = voteRepository.findAllByFkpoll(poll.getId());
                    int yes = 0;
                    int no = 0;
                    for (Vote v : votes) {
                        if (v.isYes()) {
                            yes++;
                        } else {
                            no++;
                        }
                    }
                    VoteDistribution dist = new VoteDistribution(poll.getId(), yes, no);
                    return new ResponseEntity<VoteDistribution>(dist,  HttpStatus.OK);
                })
                .orElse(new ResponseEntity<VoteDistribution>(HttpStatus.NO_CONTENT));  //TODO: Bad workaround
    }

    @GetMapping("/polls/distribution")
    public ResponseEntity<List<VoteDistribution>> getAllDistributionFromPolls() {
        List<Poll> polls = (List<Poll>) pollRepository.findAll();
        List<VoteDistribution> distlist = new ArrayList<>();
        for (Poll p : polls) {
            List<Vote> votes = voteRepository.findAllByFkpoll(p.getId());
            int yes = 0;
            int no = 0;
            for (Vote v : votes) {
                if (v.isYes()) {
                    yes++;
                } else {
                    no++;
                }
            }
            distlist.add(new VoteDistribution(p.getId(), yes, no));
        }
        return new ResponseEntity<List<VoteDistribution>>(distlist,  HttpStatus.OK);  //TODO: Bad workaround
    }

    @GetMapping("/polls/{id}/hasVoted/{person_id}")
    public ResponseEntity<VotedInThisPoll> getVotedInThisPoll(@PathVariable Long id, @PathVariable Long person_id) {
        return pollRepository.findById(id)
                .map(poll -> {
                    List<Vote> votes = voteRepository.findAllByFkpoll(poll.getId());
                    boolean hasVoted = false;
                    for (Vote v : votes) {
                        if (v.getFkperson() == person_id) {
                            hasVoted = true;
                            break;
                        }
                    }
                    VotedInThisPoll vitp = new VotedInThisPoll(hasVoted);
                    return new ResponseEntity<VotedInThisPoll>(vitp,  HttpStatus.OK);
                })
                .orElse(new ResponseEntity<VotedInThisPoll>(HttpStatus.NO_CONTENT));  //TODO: Bad workaround
    }


    @GetMapping("/polls/name/{name}")
    public Poll findByName(@PathVariable String name) {
        Poll poll = pollRepository.findByName(name);
        List<Vote> votes = (List<Vote>) voteRepository.findAllByFkpoll(poll.getId());
        poll.setVotes(votes);
        return poll;
    }

    @GetMapping("/polls/person/{person_id}")
    public List<Poll> findAllPollsByFkperson(@PathVariable long person_id) {
        List<Poll> polls = (List<Poll>) pollRepository.findAllByFkperson(person_id);
        for (Poll poll : polls) {
            List<Vote> votes = (List<Vote>) voteRepository.findAllByFkpoll(poll.getId());
            poll.setVotes(votes);
        }
        return polls;
    }

    @GetMapping("/polls/{id}")
    public Poll findPollById(@PathVariable long id) {
        Poll poll = pollRepository.findById(id);
        List<Vote> votes = voteRepository.findAllByFkpoll(poll.getId());
        poll.setVotes(votes);
        return poll;
    }

    @DeleteMapping("/polls/{id}")
    public void deletePollById(@PathVariable long id) {
        pollRepository.deleteById(id);
    }

    @PutMapping("/polls/{id}")
    public Poll replacePoll(@RequestBody Poll newPoll, @PathVariable Long id) {
        return pollRepository.findById(id)
                .map(poll -> {
                    poll.setName(newPoll.getName());

                    for (Vote vote : newPoll.getVotes())
                        voteRepository.save(vote);
                    new Producer(poll);
                    return pollRepository.save(poll);
                })
                .orElseGet(() -> {
                    newPoll.setId(id);
                    new Producer(newPoll);
                    return pollRepository.save(newPoll);
                });
    }

    @PostMapping("/votes")
    public Vote saveVote(@RequestBody Vote vote) {
        voteRepository.save(vote);
        return vote;
    }

    @GetMapping("/votes")
    public List<Vote> getAllVotes() {
        return (List<Vote>) voteRepository.findAll();
    }

/*    @GetMapping("/votes/person/{person}")
    public Vote findByPerson(@PathVariable Person person) {
        return voteRepository.findByPerson(person);
    }*/

    @GetMapping("/votes/poll/{poll}")
    public Vote findByPoll(@PathVariable Poll poll) {
        return voteRepository.findByPoll(poll);
    }

    @GetMapping("/votes/fkpoll/{fkpoll}")
    public List<Vote> findAllByFkpoll(@PathVariable long fkpoll) {
        return voteRepository.findAllByFkpoll(fkpoll);
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
//                    vote.setPerson(newVote.getPerson());
                    vote.setPoll(newVote.getPoll());
                    vote.setFkpoll(newVote.getFkpoll());
                    vote.setFkperson(newVote.getFkperson());
                    return voteRepository.save(vote);
                })
                .orElseGet(() -> {
                    newVote.setId(id);
                    return voteRepository.save(newVote);
                });
    }
}
