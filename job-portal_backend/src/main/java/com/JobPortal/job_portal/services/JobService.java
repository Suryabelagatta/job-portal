package com.JobPortal.job_portal.services;

import com.JobPortal.job_portal.dtos.JobDTO;
import com.JobPortal.job_portal.models.Job;

import java.util.List;

public interface JobService {
    JobDTO createJob(JobDTO jobDTO);
    JobDTO getJobById(Long id);
    List<JobDTO> getAllJobs();
    List<JobDTO> getJobsByFilters(String category, String location, String experience);
    List<JobDTO> searchJobs(String keyword);
    List<JobDTO> getRecruiterJobs();
    JobDTO updateJob(Long id, JobDTO jobDTO);
    void deleteJob(Long id);
    Job getJobEntityById(Long id);
}