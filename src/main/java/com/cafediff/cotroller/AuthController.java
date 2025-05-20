package com.cafediff.cotroller;

import com.cafediff.requestbody.LoginBody;
import com.cafediff.requestbody.RegisterBody;
import com.cafediff.responsebody.AuthResponse;
import com.cafediff.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("cafediff/auth")
public class AuthController {
    private final UserService userService;

    @GetMapping("/")
    public String index() {
        return "GGEZ";
    }

    @GetMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginBody loginBody) {
        return ResponseEntity.ok(userService.login(loginBody));
    }

    @GetMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterBody registerBody) {
        return ResponseEntity.ok(userService.register(registerBody));
    }
}
