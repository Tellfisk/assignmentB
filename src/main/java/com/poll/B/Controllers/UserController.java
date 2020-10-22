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

    @PostMapping("/users")
    public User saveUser(@RequestBody User user) {
        repository.save(user);
        return user;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return (List<User>) repository.findAll();
    }

    @GetMapping("/users/{id}")
    public User findById(@PathVariable long id) {
        return repository.findById(id);
    }

    @GetMapping("/users/username/{username}")
    public User findByUsername(@PathVariable String username) {
        return repository.findByUsername(username);
    }

    @PutMapping("/users/{id}")
    public User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setPassword(newUser.getPassword());
                    user.setAdmin(newUser.isAdmin());
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    public void deleteById(@PathVariable long id) {
        repository.deleteById(id);
    }
}
