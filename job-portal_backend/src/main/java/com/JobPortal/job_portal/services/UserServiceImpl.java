package com.JobPortal.job_portal.services;

import com.JobPortal.job_portal.controllers.AuthController;
import com.JobPortal.job_portal.dtos.UserDTO;
import com.JobPortal.job_portal.exceptions.ResourceNotFoundException;
import com.JobPortal.job_portal.exceptions.UnauthorizedException;
import com.JobPortal.job_portal.models.User;
import com.JobPortal.job_portal.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDTO getCurrentUser() {
        User user = getCurrentUserEntity();
        return convertToDTO(user);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = getUserEntityById(id);
        return convertToDTO(user);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        User currentUser = getCurrentUserEntity();

        // Ensure user can only update their own profile
        if (!currentUser.getEmail().equals(userDTO.getEmail())) {
            //logger.info("a"+currentUser.getEmail());
            throw new UnauthorizedException("You can only update your own profile");
        }

        // Update fields
        currentUser.setFullName(userDTO.getFullName());
        currentUser.setPhone(userDTO.getPhone());
        currentUser.setLocation(userDTO.getLocation());
        currentUser.setSkills(userDTO.getSkills());
        currentUser.setBio(userDTO.getBio());

        // Save and return
        User updatedUser = userRepository.save(currentUser);
        return convertToDTO(updatedUser);
    }

    @Override
    public User getUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    private User getCurrentUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("User not authenticated"));
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