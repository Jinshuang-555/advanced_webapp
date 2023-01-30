package com.example.advanced_webapp.Tables;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Embeddable
@Getter @Setter
public class List implements Serializable {
    public List() {
    }

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "char(36)")
    private String listId;
    private String name;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "list")
    java.util.List<Task> taskList;

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
