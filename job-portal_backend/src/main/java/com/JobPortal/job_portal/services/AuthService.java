package com.JobPortal.job_portal.services;

import com.JobPortal.job_portal.dtos.LoginRequest;
import com.JobPortal.job_portal.dtos.LoginResponse;
import com.JobPortal.job_portal.dtos.RegisterRequest;
import com.JobPortal.job_portal.dtos.UserDTO;

public interface AuthService {
    UserDTO register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
}