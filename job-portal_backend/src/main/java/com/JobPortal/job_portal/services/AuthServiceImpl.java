package com.JobPortal.job_portal.services;

import com.JobPortal.job_portal.controllers.AuthController;
import com.JobPortal.job_portal.dtos.LoginRequest;
import com.JobPortal.job_portal.dtos.LoginResponse;
import com.JobPortal.job_portal.dtos.RegisterRequest;
import com.JobPortal.job_portal.dtos.UserDTO;
import com.JobPortal.job_portal.exceptions.BadRequestException;
import com.JobPortal.job_portal.exceptions.UnauthorizedException;
import com.JobPortal.job_portal.models.User;
import com.JobPortal.job_portal.repositories.UserRepository;
import com.JobPortal.job_portal.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO register(RegisterRequest registerRequest) {
        // Check if user already exists
        logger.info("Reached someMethod()");
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException("Email is already taken!");
        }

        // Create new user
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFullName(registerRequest.getFullName());
        user.setRole(registerRequest.getRole());
        user.setPhone(registerRequest.getPhone());
        user.setLocation(registerRequest.getLocation());
        user.setSkills(registerRequest.getSkills());
        user.setBio(registerRequest.getBio());

        // Save user
        User savedUser = userRepository.save(user);

        // Convert to DTO and return
        return convertToDTO(savedUser);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        UserDTO userDTO = convertToDTO(user);
        return new LoginResponse(token, userDTO);
    }


    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFullName(user.getFullName());
        userDTO.setRole(user.getRole());
        userDTO.setPhone(user.getPhone());
        userDTO.setLocation(user.getLocation());
        userDTO.setSkills(user.getSkills());
        userDTO.setBio(user.getBio());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        return userDTO;
    }
}