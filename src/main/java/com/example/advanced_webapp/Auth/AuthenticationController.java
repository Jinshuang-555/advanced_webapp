package com.example.advanced_webapp.Auth;

import com.example.advanced_webapp.Config.JwtService;
import com.example.advanced_webapp.Exceptions.EmailExistException;
import com.example.advanced_webapp.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/v1/app/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterRequest registerRequest) throws IOException, EmailExistException {
        if (!userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.status(201).body(authenticationService.register(registerRequest));
        } else {
            throw new EmailExistException("Email Already Registered");
        }

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout (HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        return ResponseEntity.ok(jwtService.expireToken(token) + "successfully logout");
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam String email,
                                         @RequestParam String token) {
        authenticationService.verify(email, token);
        return ResponseEntity.ok("you're verified, please try to login");
    }

}
