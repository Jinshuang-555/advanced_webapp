package com.example.advanced_webapp.Controllers.Tag;

import com.example.advanced_webapp.Config.JwtService;
import com.example.advanced_webapp.Repositories.TagRepository;
import com.example.advanced_webapp.Repositories.UserRepository;
import com.example.advanced_webapp.Tables.Tag;
import com.example.advanced_webapp.Tables.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/v1/app/tag")
@RequiredArgsConstructor
public class TagController {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    private final TagRepository tagRepository;
    @PostMapping
    public ResponseEntity<String> addTag(HttpServletRequest request, @RequestBody TagRequest tagRequest) {
        String token = request.getHeader("Authorization").substring(7);
        String userName = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(userName).orElseThrow();

        String name = tagRequest.getName();
        Optional<Tag> addedTag = tagRepository.findByName(name);
        if (addedTag.isPresent()) {
            return ResponseEntity.status(405).body("This tag is already added, please choose a different name");
        }
        Tag tag = Tag.builder()
                .name(name)
                .user(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        tagRepository.save(tag);
        return ResponseEntity.status(200).body("Tag " + name + " is added");
    }
}
