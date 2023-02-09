package com.example.advanced_webapp.Controllers.List;

import com.example.advanced_webapp.Config.JwtService;
import com.example.advanced_webapp.Repositories.ListRepository;
import com.example.advanced_webapp.Repositories.UserRepository;
import com.example.advanced_webapp.Tables.List;
import com.example.advanced_webapp.Tables.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/list")
@RequiredArgsConstructor
public class ListController {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    private final ListRepository listRepository;

    @PostMapping
    public ResponseEntity<String> addList(HttpServletRequest request, @RequestBody ListRequest listRequest) {
        String token = request.getHeader("Authorization").substring(7);
        String userName = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(userName).orElseThrow();

        Optional<List> listAdded = listRepository.findByName(listRequest.getName());
        if (listAdded.isPresent()) {
            return ResponseEntity.status(405).body("List already added, please view the existing list or change a new new name");
        }

        List list = List.builder()
                .name(listRequest.getName())
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();

        listRepository.save(list);
        return ResponseEntity.ok("list " + listRequest.getName() + " is added" );
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<String> deleteList(@RequestBody ListRequest listRequest) {
        Optional<List> list = listRepository.findByName(listRequest.getName());
        if (!list.isPresent()) {
            return ResponseEntity.status(404).body("List not found, please provide a valid list name");
        }
        listRepository.deleteByName(listRequest.getName());
        return ResponseEntity.ok("deleted list: " + listRequest.getName());
    }
}
