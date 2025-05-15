package com.JobPortal.job_portal.services;

import com.JobPortal.job_portal.dtos.UserDTO;
import com.JobPortal.job_portal.models.User;

public interface UserService {
    UserDTO getCurrentUser();
    UserDTO getUserById(Long id);
    UserDTO updateUser(UserDTO userDTO);
    User getUserEntityById(Long id);
}