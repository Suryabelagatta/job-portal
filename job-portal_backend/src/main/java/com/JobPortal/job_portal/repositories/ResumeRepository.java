package com.JobPortal.job_portal.repositories;

import com.JobPortal.job_portal.models.Resume;
import com.JobPortal.job_portal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findByUser(User user);
}
