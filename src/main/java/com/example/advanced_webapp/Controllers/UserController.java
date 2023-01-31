package com.example.advanced_webapp.Controllers;

import com.example.advanced_webapp.Config.JwtService;
import com.example.advanced_webapp.Repositories.UserRepository;
import com.example.advanced_webapp.Tables.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final JwtService jwtService;
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Optional<User>> getUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String userName = jwtService.extractUsername(token);
        return ResponseEntity.ok(userRepository.findByEmail(userName));
    }
    @DeleteMapping
    @Transactional
    public ResponseEntity<String> deleteUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String userName = jwtService.extractUsername(token);
        userRepository.deleteUsersByEmail(userName);
        return ResponseEntity.ok("deleted user " + userName);
    }
}
