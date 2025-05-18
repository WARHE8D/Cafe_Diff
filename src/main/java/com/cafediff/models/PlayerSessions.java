package com.cafediff.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class PlayerSessions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(mappedBy = "playerSessions")
    private User userId;

    @OneToOne(mappedBy = "playerSessions")
    private GamingSystems systemId;
    private Date startTime;
    private Date endTime;
    private boolean isActive;
}
