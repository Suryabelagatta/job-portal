package com.JobPortal.job_portal.services;

import com.JobPortal.job_portal.dtos.JobDTO;
import com.JobPortal.job_portal.dtos.UserDTO;
import com.JobPortal.job_portal.exceptions.ResourceNotFoundException;
import com.JobPortal.job_portal.exceptions.UnauthorizedException;
import com.JobPortal.job_portal.models.Job;
import com.JobPortal.job_portal.models.Role;
import com.JobPortal.job_portal.models.User;
import com.JobPortal.job_portal.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserService userService;

    @Override
    public JobDTO createJob(JobDTO jobDTO) {
        User recruiter = userService.getUserEntityById(getCurrentUserId());

        // Validate user is a recruiter
        if (recruiter.getRole() != Role.RECRUITER) {
            throw new UnauthorizedException("Only recruiters can post jobs");
        }

        // Create job entity
        Job job = new Job();
        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setCompany(jobDTO.getCompany());
        job.setLocation(jobDTO.getLocation());
        job.setCategory(jobDTO.getCategory());
        job.setExperienceRequired(jobDTO.getExperienceRequired());
        job.setSalary(jobDTO.getSalary());
        job.setRecruiter(recruiter);
        job.setStatus("ACTIVE");

        // Save and return
        Job savedJob = jobRepository.save(job);
        return convertToDTO(savedJob);
    }

    @Override
    public JobDTO getJobById(Long id) {
        Job job = getJobEntityById(id);
        return convertToDTO(job);
    }

    @Override
    public List<JobDTO> getAllJobs() {
        return jobRepository.findByStatus("ACTIVE").stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

//    @Override
//    public List<JobDTO> getJobsByFilters(String category, String location, String experience) {
//        System.out.println("Filter Params -> category: " + category + ", location: " + location + ", experience: " + experience);
//        return jobRepository.findJobsByFilters(category, location, experience).stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
@Override
public List<JobDTO> getJobsByFilters(String category, String location, String experience) {
    category = (category != null && !category.trim().isEmpty()) ? category : null;
    location = (location != null && !location.trim().isEmpty()) ? location : null;
    experience = (experience != null && !experience.trim().isEmpty()) ? experience : null;

    System.out.println("Filter Params -> category: " + category + ", location: " + location + ", experience: " + experience);

    return jobRepository.findJobsByFilters(category, location, experience).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
}

    @Override
    public List<JobDTO> searchJobs(String keyword) {
        return jobRepository.searchJobs(keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobDTO> getRecruiterJobs() {
        User recruiter = userService.getUserEntityById(getCurrentUserId());

        // Validate user is a recruiter
        if (recruiter.getRole() != Role.RECRUITER) {
            throw new UnauthorizedException("Only recruiters can view their posted jobs");
        }

        return jobRepository.findByRecruiter(recruiter).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public JobDTO updateJob(Long id, JobDTO jobDTO) {
        Job job = getJobEntityById(id);
        User currentUser = userService.getUserEntityById(getCurrentUserId());

        // Check if user is the recruiter who posted this job
        if (!job.getRecruiter().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You can only update jobs that you posted");
        }

        // Update fields
        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setCompany(jobDTO.getCompany());
        job.setLocation(jobDTO.getLocation());
        job.setCategory(jobDTO.getCategory());
        job.setExperienceRequired(jobDTO.getExperienceRequired());
        job.setSalary(jobDTO.getSalary());
        if (jobDTO.getStatus() != null) {
            job.setStatus(jobDTO.getStatus());
        }

        // Save and return
        Job updatedJob = jobRepository.save(job);
        return convertToDTO(updatedJob);
    }

    @Override
    public void deleteJob(Long id) {
        Job job = getJobEntityById(id);
        User currentUser = userService.getUserEntityById(getCurrentUserId());

        // Check if user is the recruiter who posted this job
        if (!job.getRecruiter().getId().equals(currentUser.getId())) {
            throw new UnauthorizedException("You can only delete jobs that you posted");
        }

        // Soft delete by changing status
        job.setStatus("DELETED");
        jobRepository.save(job);
    }

    @Override
    public Job getJobEntityById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));
    }

    private Long getCurrentUserId() {
        return userService.getCurrentUser().getId();
    }

    private JobDTO convertToDTO(Job job) {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setCompany(job.getCompany());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setCategory(job.getCategory());
        jobDTO.setExperienceRequired(job.getExperienceRequired());
        jobDTO.setSalary(job.getSalary());
        jobDTO.setStatus(job.getStatus());
        jobDTO.setPostedDate(job.getPostedDate());

        // Set recruiter details
        UserDTO recruiterDTO = new UserDTO();
        recruiterDTO.setId(job.getRecruiter().getId());
        recruiterDTO.setFullName(job.getRecruiter().getFullName());
        recruiterDTO.setEmail(job.getRecruiter().getEmail());
        jobDTO.setRecruiter(recruiterDTO);

        return jobDTO;
    }
}