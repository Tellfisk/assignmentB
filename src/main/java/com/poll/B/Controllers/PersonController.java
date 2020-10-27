package com.poll.B.Controllers;

import com.poll.B.Exceptions.PersonNotFoundException;
import com.poll.B.Repositories.PersonRepository;
import com.poll.B.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonRepository repository;

    @PostMapping
    public Person saveUser(@RequestBody Person user) {
        repository.save(user);
        return user;
    }

    @GetMapping
    public List<Person> getAllPersons() {
        return (List<Person>) repository.findAll();
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    @GetMapping("/name/{name}")
    public Person findByname(@PathVariable String name) {
        return repository.findByName(name);
    }

    @PutMapping("/{id}")
    public Person replaceUser(@RequestBody Person newPerson, @PathVariable Long id) {

        return repository.findById(id)
                .map(user -> {
                    user.setName(newPerson.getName());
                    user.setPassword(newPerson.getPassword());
                    user.setAdmin(newPerson.isAdmin());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newPerson.setId(id);
                    return repository.save(newPerson);
                });
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        repository.deleteById(id);
    }
}
