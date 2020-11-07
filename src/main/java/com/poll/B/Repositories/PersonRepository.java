package com.poll.B.Repositories;

import com.poll.B.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Long> {

    Person findByEmail(String email);

    Person findById(long id);

    void deleteById(long id);
}

