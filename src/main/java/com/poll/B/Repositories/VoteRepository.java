package com.poll.B.Repositories;

import com.poll.B.Person;
import com.poll.B.Vote;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote, Long> {

    Vote findByPerson(Person person);

    Vote findById(long id);

    void deleteById(long id);
}

