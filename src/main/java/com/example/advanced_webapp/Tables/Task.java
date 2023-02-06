package com.example.advanced_webapp.Tables;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity @Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "char(36)")
    @Id
    private String taskId;

    @ManyToOne
    @JoinColumn(name = "listId")
    private List list;
    private String summary;
    private String task;
    private LocalDateTime dueDate;
    private int priority; // 1-high 2-medium 3-low
    private int status; // 1-todo 2-completed 3-overdue

    @OneToMany(mappedBy = "task")
    java.util.List<Remainder> remainderList;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "task_tags",
            joinColumns = @JoinColumn(name = "taskId"),
            inverseJoinColumns = @JoinColumn(name = "tagId"))
    private Set<Tag> tags = new HashSet<>();

}
