package com.JobPortal.job_portal.services;

import com.JobPortal.job_portal.models.Resume;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {
    Resume storeResume(MultipartFile file);
    List<Resume> getUserResumes();
    Resume getResumeById(Long id);
    void deleteResume(Long id);
}
