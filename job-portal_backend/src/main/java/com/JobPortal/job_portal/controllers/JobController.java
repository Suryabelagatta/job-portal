package com.JobPortal.job_portal.controllers;

import com.JobPortal.job_portal.dtos.ApiResponse;
import com.JobPortal.job_portal.dtos.JobDTO;
import com.JobPortal.job_portal.services.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping
    public ResponseEntity<ApiResponse> createJob(@Valid @RequestBody JobDTO jobDTO) {
        JobDTO job = jobService.createJob(jobDTO);
        return ResponseEntity.ok(new ApiResponse(true, "Job created", job));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllJobs() {
        List<JobDTO> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(new ApiResponse(true, "All jobs fetched", jobs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getJobById(@PathVariable Long id) {
        JobDTO job = jobService.getJobById(id);
        return ResponseEntity.ok(new ApiResponse(true, "Job fetched", job));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchJobs(@RequestParam String keyword) {
        List<JobDTO> jobs = jobService.searchJobs(keyword);
        return ResponseEntity.ok(new ApiResponse(true, "Search results", jobs));
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse> getJobsByFilter(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String experience) {
        List<JobDTO> jobs = jobService.getJobsByFilters(category, location, experience);
        return ResponseEntity.ok(new ApiResponse(true, "Filtered jobs", jobs));
    }

    @GetMapping("/recruiter")
    public ResponseEntity<ApiResponse> getRecruiterJobs() {
        List<JobDTO> jobs = jobService.getRecruiterJobs();
        return ResponseEntity.ok(new ApiResponse(true, "Recruiter's jobs", jobs));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateJob(@PathVariable Long id, @Valid @RequestBody JobDTO jobDTO) {
        JobDTO updated = jobService.updateJob(id, jobDTO);
        return ResponseEntity.ok(new ApiResponse(true, "Job updated", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.ok(new ApiResponse(true, "Job deleted"));
    }
}
