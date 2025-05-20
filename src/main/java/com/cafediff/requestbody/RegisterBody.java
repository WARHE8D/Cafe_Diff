package com.cafediff.requestbody;

import com.cafediff.models.Games;
import lombok.Data;

import java.util.Set;

@Data
public class RegisterBody {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private Set<Games> frequentGames;

}
