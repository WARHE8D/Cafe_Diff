package com.cafediff.service;

import com.cafediff.requestbody.LoginBody;
import com.cafediff.requestbody.RegisterBody;

public interface UserService {
    public String register(RegisterBody registerBody);
    public String login(LoginBody loginBody);
}
