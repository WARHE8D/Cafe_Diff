package com.cafediff.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GamingSystems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private GamingPlatform gamingPlatform;
    private String status;

    @ManyToMany(mappedBy = "gamingSystems")
    private Set<Games> games;
}
