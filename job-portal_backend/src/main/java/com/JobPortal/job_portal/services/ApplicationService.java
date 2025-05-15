package com.JobPortal.job_portal.services;

import com.JobPortal.job_portal.dtos.ApplicationDTO;

import java.util.List;

public interface ApplicationService {
    ApplicationDTO applyForJob(ApplicationDTO applicationDTO);
    ApplicationDTO getApplicationById(Long id);
    List<ApplicationDTO> getUserApplications();
    List<ApplicationDTO> getJobApplications(Long jobId);
    ApplicationDTO updateApplicationStatus(Long id, String status);
}
