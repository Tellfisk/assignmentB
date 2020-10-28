package com.poll.B.Controllers;

import com.poll.B.Exceptions.PersonNotFoundException;
import com.poll.B.Repositories.PersonRepository;
import com.poll.B.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequestMapping("/persons")
public class PersonController {

    //@Autowired
    private PersonRepository personRepository;

    @PostMapping
    public Person saveUser(@RequestBody Person user) {
        personRepository.save(user);
        return user;
    }

    @GetMapping
    public List<Person> getAllPersons() {
        return (List<Person>) personRepository.findAll();
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    @GetMapping("/name/{name}")
    public Person findByName(@PathVariable String name) {
        return personRepository.findByName(name);
    }

    @PutMapping("/{id}")
    public Person replaceUser(@RequestBody Person newPerson, @PathVariable Long id) {

        return personRepository.findById(id)
                .map(user -> {
                    user.setName(newPerson.getName());
                    user.setPassword(newPerson.getPassword());
                    user.setAdmin(newPerson.isAdmin());
                    return personRepository.save(user);
                })
                .orElseGet(() -> {
                    newPerson.setId(id);
                    return personRepository.save(newPerson);
                });
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id) {
        personRepository.deleteById(id);
    }
}
