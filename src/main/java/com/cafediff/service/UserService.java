package com.cafediff.service;

import com.cafediff.requestbody.LoginBody;
import com.cafediff.requestbody.RegisterBody;
import com.cafediff.responsebody.AuthResponse;

public interface UserService {
    public AuthResponse register(RegisterBody registerBody);
    public AuthResponse login(LoginBody loginBody);
}
