package com.cafediff.requestbody;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginBody {

    @NotNull(message = "username cannot be null")
    private String username;

    @NotNull(message = "password cannot be null")
    private String password;
}
