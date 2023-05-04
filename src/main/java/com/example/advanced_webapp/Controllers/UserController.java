package com.example.advanced_webapp.Controllers;

import com.example.advanced_webapp.Config.JwtService;
import com.example.advanced_webapp.Repositories.UserRepository;
import com.example.advanced_webapp.Tables.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/v1/app/user")
@RequiredArgsConstructor
public class UserController {
    private final JwtService jwtService;
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Optional<User>> getUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String userName = jwtService.extractUsername(token);
        User user = userRepository.findUserByEmail(userName);
        if (user != null) {
            return ResponseEntity.ok(userRepository.findByEmail(userName));
        } else {
            throw new UsernameNotFoundException("didn't find the user with the username");
        }
    }
    @DeleteMapping
    @Transactional
    public ResponseEntity<String> deleteUser(HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization").substring(7);
        String userName = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(userName).orElseThrow();
        if (user == null) {
            return ResponseEntity.status(404).body("Please register first");
        } else if (!user.isVerified()) {
            return ResponseEntity.status(403).body("Please verify your account first");
        }
        userRepository.deleteUsersByEmail(userName);
        return ResponseEntity.ok("deleted user " + userName);
    }
}
