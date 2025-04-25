package io.github.simonnozaki.longtimenosee.domain.note;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@jakarta.persistence.Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notes")
public class Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @Column(name = "created_at")
    private String createdAt;
    @Column(name = "updated_at")
    private String updatedAt;

    @PrePersist
    public void onPersist() {
        var now = LocalDateTime.now().toString();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now().toString();
    }
}
