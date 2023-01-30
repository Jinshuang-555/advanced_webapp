package com.example.advanced_webapp.Controllers;

import com.example.advanced_webapp.Repositories.UserRepository;
import com.example.advanced_webapp.Tables.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public ResponseEntity<User> getUser(@RequestParam String userId) {
        return ResponseEntity.ok(userRepository.findByUserId(userId));

    }
    @DeleteMapping("/v1/user")
    public void deleteUser(@RequestParam String userId) {
        User found = userRepository.findByUserId(userId);
        System.out.println(found);
        userRepository.delete(found);
    }
}
