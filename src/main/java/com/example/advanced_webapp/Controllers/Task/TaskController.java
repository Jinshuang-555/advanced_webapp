package com.example.advanced_webapp.Controllers.Task;

import com.example.advanced_webapp.Config.JwtService;
import com.example.advanced_webapp.Controllers.Tag.TagRequest;
import com.example.advanced_webapp.Kafka.KafkaProducer;
import com.example.advanced_webapp.Kafka.Message.TaskMessage;
import com.example.advanced_webapp.Repositories.ListRepository;
import com.example.advanced_webapp.Repositories.TagRepository;
import com.example.advanced_webapp.Repositories.TaskRepository;
import com.example.advanced_webapp.Repositories.UserRepository;
import com.example.advanced_webapp.Tables.List;
import com.example.advanced_webapp.Tables.Tag;
import com.example.advanced_webapp.Tables.Task;
import com.example.advanced_webapp.Tables.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

@RestController
@RequestMapping("/v1/app/task")
@RequiredArgsConstructor
public class TaskController {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ListRepository listRepository;
    private final TagRepository tagRepository;
    private final TaskRepository taskRepository;

    private final KafkaProducer kafkaProducer;

    @PostMapping
    public ResponseEntity<String> addTask(HttpServletRequest request, @RequestBody TaskRequest taskRequest) {
        String token = request.getHeader("Authorization").substring(7);
        String userName = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(userName).orElseThrow();

        List list = listRepository.findListByName(taskRequest.getListName());
        // create a new list if not exist
        if (list==null) {
            list = List.builder()
                    .name(taskRequest.getListName())
                    .updatedAt(LocalDateTime.now())
                    .createdAt(LocalDateTime.now())
                    .user(user)
                    .build();
            listRepository.save(list);
        }

        Tag tag = tagRepository.findTagByName(taskRequest.getTag());
        // add a new tag if not exist
        if (tag == null) {
            tag = Tag.builder()
                    .name(taskRequest.getTag())
                    .user(user)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            tagRepository.save(tag);
        }

        Task task = Task.builder()
                .summary(taskRequest.getSummary())
                .task(taskRequest.getTask())
                .list(list)
                .priority(taskRequest.getPriority())
                .dueDate(taskRequest.getDueDate())
                .tags(new HashSet<>())
                .build();
        TaskMessage taskMessage = new TaskMessage(taskRequest.listName, taskRequest.task,
                task.getSummary(),taskRequest.getDueDate(),taskRequest.getPriority(),taskRequest.getTag());
        kafkaProducer.sendSqlMessage("sql", taskMessage);
        task.getTags().add(tag);
        taskRepository.save(task);
        return ResponseEntity.status(200).body("Task " + taskRequest.getTask() + " is added");
    }
 }
