package com.cafediff.service;

import com.cafediff.config.AppConfig;
import com.cafediff.config.JwtService;
import com.cafediff.models.Role;
import com.cafediff.models.User;
import com.cafediff.repository.UserRepository;
import com.cafediff.requestbody.LoginBody;
import com.cafediff.requestbody.RegisterBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AppConfig appConfig;

    @Override
    public String register(RegisterBody registerBody) {
        if(userRepository.findByUsername(registerBody.getUsername()).isPresent()){
            return "Username already exists";
        }
        User user = new User();
        user.setUsername(registerBody.getUsername());
        user.setPassword(appConfig.passwordEncoder().encode(registerBody.getPassword()));
        user.setEmail(registerBody.getEmail());
        user.setFirstName(registerBody.getFirstName());
        user.setLastName(registerBody.getLastName());
        user.setEnabled(true);
        user.setFrequentGames(registerBody.getFrequentGames());
        user.setRole(Role.CUSTOMER);
        userRepository.save(user);
        return jwtService.gennerateToken(user);

    }

    @Override
    public String login(LoginBody loginBody) {
        User dbUser = userRepository.findByUsername(loginBody.getUsername()).orElse(null);
        if (dbUser == null) {
            return "User Not Found";
        }
        User u = new User();
        u.setUsername(loginBody.getUsername());
        u.setPassword(appConfig.passwordEncoder().encode(loginBody.getPassword()));
        if(dbUser.getPassword().equals(u.getPassword())) {
            return jwtService.gennerateToken(u);
        }
        return "Invalid Credentials";
    }
}
