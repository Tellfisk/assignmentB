package com.poll.B.Controllers;

import com.poll.B.User;
import com.poll.B.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository repository;

    @PostMapping("/saveUser")
    public String saveUser(@RequestBody User User) {
        repository.save(User);
        return "User saved";
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return (List<User>) repository.findAll();
    }

    @GetMapping("/getUserByUsername/{username}")
    public User findByUsername(@PathVariable String username) {
        return repository.findByUsername(username);
    }

    @DeleteMapping
    public void deleteById(@PathVariable long id) {
        repository.deleteById(id);
    }

}

