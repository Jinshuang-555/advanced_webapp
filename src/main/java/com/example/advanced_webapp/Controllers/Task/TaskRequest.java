package com.example.advanced_webapp.Controllers.Task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {

    String listName;
    String task;
    String summary;
    LocalDateTime dueDate;
    int priority;
    String tag;

}
