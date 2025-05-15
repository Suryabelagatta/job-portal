package com.JobPortal.job_portal.controllers;

import com.JobPortal.job_portal.dtos.ApiResponse;
import com.JobPortal.job_portal.dtos.ApplicationDTO;
import com.JobPortal.job_portal.services.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApiResponse> apply(@Valid @RequestBody ApplicationDTO dto) {
        ApplicationDTO application = applicationService.applyForJob(dto);
        return ResponseEntity.ok(new ApiResponse(true, "Application submitted", application));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable Long id) {
        ApplicationDTO application = applicationService.getApplicationById(id);
        return ResponseEntity.ok(new ApiResponse(true, "Application fetched", application));
    }

    @GetMapping("/user")
    public ResponseEntity<ApiResponse> getUserApplications() {
        List<ApplicationDTO> list = applicationService.getUserApplications();
        return ResponseEntity.ok(new ApiResponse(true, "User applications fetched", list));
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<ApiResponse> getJobApplications(@PathVariable Long jobId) {
        List<ApplicationDTO> list = applicationService.getJobApplications(jobId);
        return ResponseEntity.ok(new ApiResponse(true, "Job applications fetched", list));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse> updateStatus(@PathVariable Long id, @RequestParam String status) {
        ApplicationDTO updated = applicationService.updateApplicationStatus(id, status);
        return ResponseEntity.ok(new ApiResponse(true, "Application status updated", updated));
    }
}

