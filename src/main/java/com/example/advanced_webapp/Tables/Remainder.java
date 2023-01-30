package com.example.advanced_webapp.Tables;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Embeddable
@Getter @Setter @NoArgsConstructor
public class Remainder {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "char(36)")
    private String remainderId;

    @ManyToOne
    @JoinColumn(name = "taskId")
    private Task task;

    private String message;
    private LocalDateTime sentTime;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // update each time when entity is changed
    @PrePersist
    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }
}
