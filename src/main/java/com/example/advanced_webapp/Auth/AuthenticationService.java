package com.example.advanced_webapp.Auth;

import com.example.advanced_webapp.Auth.VerificationToken.Token;
import com.example.advanced_webapp.Config.JwtService;
import com.example.advanced_webapp.Repositories.UserRepository;
import com.example.advanced_webapp.Tables.Role;
import com.example.advanced_webapp.Tables.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public AuthenticationResponse register(RegisterRequest registerRequest) throws IOException {
        var user = User
                .builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String token = UUID.randomUUID().toString();

        Token tokenObject = new Token(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );
        String link = "http://localhost:8080/api/v1/auth/verify?token=" + token + "&email=" + registerRequest.getEmail();
        String message = "email="+registerRequest.getEmail() + ",link=" + link;
        System.out.println(message);
        kafkaTemplate.send("registration", message);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()
                )
        );
        var user = userRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public void verify(String email, String token) {
        User user = userRepository.findUserByEmail(email);
        user.setVerified(true);
    }
}
