package com.etaskify.etaskify.dao.entity;


import com.etaskify.etaskify.model.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "deadline", nullable = false)
    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    @JsonBackReference
    private OrganizationEntity organization;

    @ManyToMany(mappedBy = "tasks")
    @JsonBackReference
    private Set<UserEntity> users;

    public Set<UserEntity> getUsers() {
        if (users == null) {
            users = new HashSet<>();
        }
        return users;
    }

}
