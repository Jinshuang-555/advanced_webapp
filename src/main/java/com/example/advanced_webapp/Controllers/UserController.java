package com.example.advanced_webapp.Controllers;

import com.example.advanced_webapp.Repositories.UserRepository;
import com.example.advanced_webapp.Tables.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/v1/user")
    public User getUser(@RequestParam String userId) {
        System.out.println(userRepository.findByUserId(userId));
        return userRepository.findByUserId(userId);

    }

    @PostMapping("v1/user/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User registeredUser = userRepository.save(user);
        return ResponseEntity.ok(registeredUser);
    }

    @DeleteMapping("/v1/user")
    public void deleteUser(@RequestParam String userId) {
        User found = userRepository.findByUserId(userId);
        System.out.println(found);
        userRepository.delete(found);
    }
}
