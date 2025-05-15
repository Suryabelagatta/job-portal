package com.JobPortal.job_portal.dtos;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

public class ApplicationDTO {
    private Long id;

    @NotNull(message = "Job is required")
    private Long jobId;

    private JobDTO job;
    private UserDTO applicant;
    private String coverLetter;
    private Long resumeId;
    private String status;
    private Date appliedDate;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public JobDTO getJob() {
        return job;
    }

    public void setJob(JobDTO job) {
        this.job = job;
    }

    public UserDTO getApplicant() {
        return applicant;
    }

    public void setApplicant(UserDTO applicant) {
        this.applicant = applicant;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(Date appliedDate) {
        this.appliedDate = appliedDate;
    }
}
