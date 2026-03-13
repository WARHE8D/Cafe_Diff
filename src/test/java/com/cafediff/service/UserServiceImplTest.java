package com.cafediff.service;

import com.cafediff.config.JwtService;
import com.cafediff.models.User;
import com.cafediff.repository.UserRepository;
import com.cafediff.requestbody.LoginBody;
import com.cafediff.requestbody.RegisterBody;
import com.cafediff.responsebody.AuthResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder pwdEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    void register_shouldCreateUserAndReturnToken() {

        RegisterBody body = new RegisterBody();
        body.setUsername("john");
        body.setPassword("123456");
        body.setEmail("john@mail.com");
        body.setFirstName("John");
        body.setLastName("Doe");

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.empty());

        when(pwdEncoder.encode("123456"))
                .thenReturn("encodedPwd");

        when(jwtService.gennerateToken(any(User.class)))
                .thenReturn("jwt-token");

        AuthResponse response = authService.register(body);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_shouldThrowException_whenUserExists() {

        RegisterBody body = new RegisterBody();
        body.setUsername("john");

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(new User()));

        assertThrows(
                UsernameNotFoundException.class,
                () -> authService.register(body)
        );

        verify(userRepository, never()).save(any());
    }

    @Test
    void login_shouldAuthenticateAndReturnToken() {

        LoginBody body = new LoginBody();
        body.setUsername("john");
        body.setPassword("123456");

        User user = new User();
        user.setUsername("john");

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));

        when(jwtService.gennerateToken(user))
                .thenReturn("jwt-token");

        AuthResponse response = authService.login(body);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());

        verify(authenticationManager).authenticate(
                any(UsernamePasswordAuthenticationToken.class)
        );
    }

    @Test
    void login_shouldThrowException_whenUserNotFound() {

        LoginBody body = new LoginBody();
        body.setUsername("john");
        body.setPassword("123456");

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> authService.login(body)
        );
    }
}