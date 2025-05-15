package com.JobPortal.job_portal.controllers;

import com.JobPortal.job_portal.dtos.ApiResponse;
import com.JobPortal.job_portal.dtos.UserDTO;
import com.JobPortal.job_portal.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getCurrentUser() {
        logger.info("Reached someMethod in user controller()");
        UserDTO user = userService.getCurrentUser();
        return ResponseEntity.ok(new ApiResponse(true, "Current user fetched", user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(new ApiResponse(true, "User fetched", user));
    }

    @PutMapping
    public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO updated = userService.updateUser(userDTO);
        return ResponseEntity.ok(new ApiResponse(true, "User updated", updated));
    }
}
