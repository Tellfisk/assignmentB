package com.poll.B.Repositories;

import com.poll.B.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    User findById(long id);
}

