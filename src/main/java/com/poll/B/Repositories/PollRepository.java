package com.poll.B.Repositories;

import com.poll.B.Poll;
import org.springframework.data.repository.CrudRepository;

public interface PollRepository extends CrudRepository<Poll, Long> {

    Poll findByName(String name);

    Poll findById(long id);

    void deleteById(long id);
}

