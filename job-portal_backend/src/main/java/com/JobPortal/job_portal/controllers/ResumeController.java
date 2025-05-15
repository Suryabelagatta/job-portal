package com.JobPortal.job_portal.controllers;

import com.JobPortal.job_portal.dtos.ApiResponse;
import com.JobPortal.job_portal.models.Resume;
import com.JobPortal.job_portal.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<ApiResponse> uploadResume(@RequestParam("file") MultipartFile file) {
        Resume resume = fileStorageService.storeResume(file);
        return ResponseEntity.ok(new ApiResponse(true, "Resume uploaded", resume));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getUserResumes() {
        List<Resume> list = fileStorageService.getUserResumes();
        return ResponseEntity.ok(new ApiResponse(true, "User resumes", list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getResumeById(@PathVariable Long id) {
        Resume resume = fileStorageService.getResumeById(id);
        return ResponseEntity.ok(new ApiResponse(true, "Resume fetched", resume));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteResume(@PathVariable Long id) {
        fileStorageService.deleteResume(id);
        return ResponseEntity.ok(new ApiResponse(true, "Resume deleted"));
    }
}

