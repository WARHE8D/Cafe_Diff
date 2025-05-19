package com.cafediff.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Games {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotEmpty(message = "game name cannot be empty")
    private String name;
    @Column(nullable = false)
    @NotEmpty(message = "genre cannot be empty")
    private String genre;

    private String description;

    @ElementCollection
    private Set<GamingPlatform> gamingPlatform;

    @ManyToOne
    @JoinColumn(name = "gaming_system_id")
    private GamingSystems gamingSystemId;
}
