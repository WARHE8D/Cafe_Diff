package com.cafediff.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

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
    private String platform;
}
