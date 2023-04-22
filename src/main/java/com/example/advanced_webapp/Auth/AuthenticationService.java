package com.example.advanced_webapp.Auth;

import com.example.advanced_webapp.Auth.VerificationToken.Token;
import com.example.advanced_webapp.Auth.VerificationToken.TokenRepository;
import com.example.advanced_webapp.Config.JwtService;
import com.example.advanced_webapp.Email.EmailService;
import com.example.advanced_webapp.Kafka.KafkaProducer;
import com.example.advanced_webapp.Kafka.Message.EmailMessage;
import com.example.advanced_webapp.Repositories.UserRepository;
import com.example.advanced_webapp.Tables.Role;
import com.example.advanced_webapp.Tables.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final KafkaProducer kafkaProducer;
    private final EmailService emailService;

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

        tokenRepository.save(tokenObject);

        String link = "https://istio.k8s.csye6225jinshuang.me/api/v1/auth/verify?token=" + token + "&email=" + registerRequest.getEmail();
        EmailMessage message = new EmailMessage(registerRequest.getEmail(), link);
//        kafkaProducer.sendMessage("registration", message);
        emailService.send(message.getEmail(), message.getLink());
        System.out.println("link: " + link);
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

    @Transactional
    public void verify(String email, String token) {

        Token confirmationToken = tokenRepository
                .findByToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }
        tokenRepository.updateConfirmedAt(token,LocalDateTime.now());
        tokenRepository.updateConfirmedAt(token, LocalDateTime.now());
        userRepository.verifyUser(email);
    }
}
