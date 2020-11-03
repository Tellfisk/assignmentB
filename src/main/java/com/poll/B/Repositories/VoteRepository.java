package com.poll.B.Repositories;

import com.poll.B.Person;
import com.poll.B.Poll;
import com.poll.B.Vote;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VoteRepository extends CrudRepository<Vote, Long> {

    Vote findByPoll(Poll poll);

//    Vote findByPerson(Person person);

    Vote findById(long id);

    List<Vote> findAllByFkpoll(long id);

    List<Vote> findAllByFkperson(Long id);

    void deleteById(long id);
}