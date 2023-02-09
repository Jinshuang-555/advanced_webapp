package com.example.advanced_webapp.Kafka.Message;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskMessage {
    String listName;
    String task;
    String summary;
    LocalDateTime dueDate;
    int priority;
    String tag;
    public TaskMessage(@JsonProperty("listName") String listName, @JsonProperty("task") String task, @JsonProperty("summary") String summary, @JsonProperty("dueDate") LocalDateTime dueDate, @JsonProperty("priority") int priority, @JsonProperty("tag") String tag) {
        this.listName = listName;
        this.task = task;
        this.summary = summary;
        this.dueDate = dueDate;
        this.priority = priority;
        this.tag = tag;
    }
    
}
