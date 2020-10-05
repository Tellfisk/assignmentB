package com.poll.B.Controllers;

import com.poll.B.Poll;
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

    @PutMapping("/user/{id}")
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
}
