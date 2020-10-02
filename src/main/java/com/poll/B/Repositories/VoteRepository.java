package com.poll.B.Repositories;

import com.poll.B.User;
import com.poll.B.Vote;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote, Long> {

    Vote findByUser(User user);

    Vote findById(long id);

    void deleteById(long id);
}

