package com.example.advanced_webapp.Tables;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Embeddable
@Getter @Setter @NoArgsConstructor
public class Tag implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "char(36)")
    private String tagId;
    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;
    @NotBlank
    private String name;
    @CreationTimestamp
    private LocalDateTime createdAt;
    // set each time entity is persisted
    private LocalDateTime updatedAt;
    // update each time when entity is changed
    @PrePersist
    @PreUpdate
    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    @ManyToMany(mappedBy = "tags")
    private Set<Task> tasks = new HashSet<>();

}
