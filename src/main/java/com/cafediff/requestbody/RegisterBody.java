package com.cafediff.requestbody;

import com.cafediff.models.Games;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterBody {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private Set<Games> frequentGames;

}
