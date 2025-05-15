package com.JobPortal.job_portal.services;

import com.JobPortal.job_portal.dtos.ApplicationDTO;
import com.JobPortal.job_portal.dtos.JobDTO;
import com.JobPortal.job_portal.dtos.UserDTO;
import com.JobPortal.job_portal.exceptions.BadRequestException;
import com.JobPortal.job_portal.exceptions.ResourceNotFoundException;
import com.JobPortal.job_portal.exceptions.UnauthorizedException;
import com.JobPortal.job_portal.models.*;
import com.JobPortal.job_portal.repositories.ApplicationRepository;
import com.JobPortal.job_portal.repositories.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JobService jobService;

    @Override
    public ApplicationDTO applyForJob(ApplicationDTO applicationDTO) {
        User applicant = userService.getUserEntityById(getCurrentUserId());

        // Validate user is a job seeker
        if (applicant.getRole() != Role.JOB_SEEKER) {
            throw new UnauthorizedException("Only job seekers can apply for jobs");
        }

        Job job = jobService.getJobEntityById(applicationDTO.getJobId());

        // Check if already applied
        if (applicationRepository.existsByJobAndApplicant(job, applicant)) {
            throw new BadRequestException("You have already applied for this job");
        }

        // Create application
        Application application = new Application();
        application.setJob(job);
        application.setApplicant(applicant);
        application.setCoverLetter(applicationDTO.getCoverLetter());
        application.setStatus("PENDING");

        // Set resume if provided
        if (applicationDTO.getResumeId() != null) {
            Resume resume = resumeRepository.findById(applicationDTO.getResumeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));

            if (!resume.getUser().getId().equals(applicant.getId())) {
                throw new UnauthorizedException("You can only use your own resumes");
            }

            application.setResume(resume);
        }

        // Save and return
        Application savedApplication = applicationRepository.save(application);
        return convertToDTO(savedApplication);
    }

    @Override
    public ApplicationDTO getApplicationById(Long id) {
        Application application = getApplicationEntityById(id);
        User currentUser = userService.getUserEntityById(getCurrentUserId());

        // Validate user can view this application
        if (currentUser.getRole() == Role.JOB_SEEKER &&
                !application.getApplicant().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You can only view your own applications");
        } else if (currentUser.getRole() == Role.RECRUITER &&
                !application.getJob().getRecruiter().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You can only view applications for your own jobs");
        }

        return convertToDTO(application);
    }

    @Override
    public List<ApplicationDTO> getUserApplications() {
        User currentUser = userService.getUserEntityById(getCurrentUserId());

        if (currentUser.getRole() != Role.JOB_SEEKER) {
            throw new UnauthorizedException("Only job seekers can view their applications");
        }

        return applicationRepository.findByApplicant(currentUser).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationDTO> getJobApplications(Long jobId) {
        User currentUser = userService.getUserEntityById(getCurrentUserId());
        Job job = jobService.getJobEntityById(jobId);

        // Validate user is the recruiter for this job
        if (currentUser.getRole() != Role.RECRUITER ||
                !job.getRecruiter().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You can only view applications for your own jobs");
        }

        return applicationRepository.findByJob(job).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationDTO updateApplicationStatus(Long id, String status) {
        Application application = getApplicationEntityById(id);
        User currentUser = userService.getUserEntityById(getCurrentUserId());

        // Validate user is the recruiter for this job
        if (currentUser.getRole() != Role.RECRUITER ||
                !application.getJob().getRecruiter().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You can only update status for applications to your own jobs");
        }

        // Update status
        application.setStatus(status);
        Application updatedApplication = applicationRepository.save(application);
        return convertToDTO(updatedApplication);
    }

    private Application getApplicationEntityById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
    }

    private Long getCurrentUserId() {
        return userService.getCurrentUser().getId();
    }

    private ApplicationDTO convertToDTO(Application application) {
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setId(application.getId());
        applicationDTO.setJobId(application.getJob().getId());
        applicationDTO.setCoverLetter(application.getCoverLetter());
        applicationDTO.setStatus(application.getStatus());
        applicationDTO.setAppliedDate(application.getAppliedDate());

        if (application.getResume() != null) {
            applicationDTO.setResumeId(application.getResume().getId());
        }

        // Set job details
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(application.getJob().getId());
        jobDTO.setTitle(application.getJob().getTitle());
        jobDTO.setCompany(application.getJob().getCompany());
        applicationDTO.setJob(jobDTO);

        // Set applicant details
        UserDTO applicantDTO = new UserDTO();
        applicantDTO.setId(application.getApplicant().getId());
        applicantDTO.setFullName(application.getApplicant().getFullName());
        applicantDTO.setEmail(application.getApplicant().getEmail());
        applicationDTO.setApplicant(applicantDTO);

        return applicationDTO;
    }
}