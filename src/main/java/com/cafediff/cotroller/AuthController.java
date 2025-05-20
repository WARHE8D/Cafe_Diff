package com.cafediff.cotroller;

import com.cafediff.config.JwtService;
import com.cafediff.models.User;
import com.cafediff.requestbody.LoginBody;
import com.cafediff.requestbody.RegisterBody;
import com.cafediff.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @GetMapping("/")
    public String index() {
        return "GGEZ";
    }

    @GetMapping("/login")
    public String login(@RequestBody @Valid LoginBody loginBody) {
        return userService.login(loginBody);
    }

    @GetMapping("/register")
    public String register(@RequestBody @Valid RegisterBody registerBody) {
        return userService.register(registerBody);
    }
}
