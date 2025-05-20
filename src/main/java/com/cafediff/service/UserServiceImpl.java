package com.cafediff.service;

import com.cafediff.config.JwtService;
import com.cafediff.models.Role;
import com.cafediff.models.User;
import com.cafediff.repository.UserRepository;
import com.cafediff.requestbody.LoginBody;
import com.cafediff.requestbody.RegisterBody;
import com.cafediff.responsebody.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder pwdEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterBody registerBody) {
        if(userRepository.findByUsername(registerBody.getUsername()).isPresent()){
            throw new UsernameNotFoundException(registerBody.getUsername()+ " user already exist");
        }
        var u = User.builder()
                .email(registerBody.getEmail())
                .firstName(registerBody.getFirstName())
                .lastName(registerBody.getLastName())
                .username(registerBody.getUsername())
                .password(pwdEncoder.encode(registerBody.getPassword()))
                .isEnabled(true)
                .role(Role.CUSTOMER)
                .build();
        userRepository.save(u);
        return AuthResponse.builder()
                .token(jwtService.gennerateToken(u))
                .build();
    }

    @Override
    public AuthResponse login(LoginBody loginBody) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginBody.getUsername(),
                        loginBody.getPassword()
                )
        );
        var u = userRepository.findByUsername(loginBody.getUsername()).orElseThrow();
        return AuthResponse.builder()
                .token(jwtService.gennerateToken(u))
                .build();
    }
}
