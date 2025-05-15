package com.JobPortal.job_portal.services;

import com.JobPortal.job_portal.exceptions.BadRequestException;
import com.JobPortal.job_portal.exceptions.ResourceNotFoundException;
import com.JobPortal.job_portal.exceptions.UnauthorizedException;
import com.JobPortal.job_portal.models.Resume;
import com.JobPortal.job_portal.models.Role;
import com.JobPortal.job_portal.models.User;
import com.JobPortal.job_portal.repositories.ResumeRepository;
import com.JobPortal.job_portal.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private UserService userService;

    @Override
    public Resume storeResume(MultipartFile file) {
        User currentUser = userService.getUserEntityById(getCurrentUserId());

        // Validate user is a job seeker
        if (currentUser.getRole() != Role.JOB_SEEKER) {
            throw new UnauthorizedException("Only job seekers can upload resumes");
        }

        // Validate file
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || originalFileName.isEmpty()) {
            throw new BadRequestException("Invalid file name");
        }

        // Validate file type (PDF or text)
        String fileExtension = FileUtils.getFileExtension(originalFileName);
        if (!fileExtension.equalsIgnoreCase("pdf") && !fileExtension.equalsIgnoreCase("txt")) {
            throw new BadRequestException("Only PDF and text files are allowed");
        }

        try {
            // Create file path
            String uniqueFileName = UUID.randomUUID() + "-" + originalFileName;
            Path fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(fileStorageLocation);
            Path targetLocation = fileStorageLocation.resolve(uniqueFileName);

            // Save file
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Create resume record
            Resume resume = new Resume();
            resume.setUser(currentUser);
            resume.setFileName(originalFileName);
            resume.setFilePath(targetLocation.toString());
            resume.setFileType(file.getContentType());
            resume.setFileSize(file.getSize());

            // Save and return
            return resumeRepository.save(resume);
        } catch (IOException ex) {
            throw new BadRequestException("Could not store file " + originalFileName + ". Please try again!");
        }
    }

    @Override
    public List<Resume> getUserResumes() {
        User currentUser = userService.getUserEntityById(getCurrentUserId());
        return resumeRepository.findByUser(currentUser);
    }

    @Override
    public Resume getResumeById(Long id) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found with id: " + id));

        User currentUser = userService.getUserEntityById(getCurrentUserId());

        // Check if user owns this resume or is the recruiter viewing an application
        if (!resume.getUser().getId().equals(currentUser.getId()) &&
                currentUser.getRole() != Role.RECRUITER) {
            throw new UnauthorizedException("You are not authorized to access this resume");
        }
        return resume;
    }

    @Override
    public void deleteResume(Long id) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found with id: " + id));

        User currentUser = userService.getUserEntityById(getCurrentUserId());

        // Check if user owns this resume
        if (!resume.getUser().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You can only delete your own resumes");
        }

        // Delete file from storage
        try {
            Path filePath = Paths.get(resume.getFilePath());
            Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            // Log but continue to delete database record
            System.err.println("Error deleting file: " + ex.getMessage());
        }

        // Delete database record
        resumeRepository.delete(resume);
    }

    private Long getCurrentUserId() {
        return userService.getCurrentUser().getId();
    }
}