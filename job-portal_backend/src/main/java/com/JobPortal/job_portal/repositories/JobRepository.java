package com.JobPortal.job_portal.repositories;

import com.JobPortal.job_portal.models.Job;
import com.JobPortal.job_portal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByRecruiter(User recruiter);

    List<Job> findByStatus(String status);

    @Query("SELECT j FROM Job j WHERE j.status = 'ACTIVE' " +
            "AND (:category IS NULL OR j.category LIKE %:category%) " +
            "AND (:location IS NULL OR j.location LIKE %:location%) " +
            "AND (:experience IS NULL OR j.experienceRequired LIKE %:experience%)")
    List<Job> findJobsByFilters(
            @Param("category") String category,
            @Param("location") String location,
            @Param("experience") String experience);

    @Query("SELECT j FROM Job j WHERE j.status = 'ACTIVE' " +
            "AND (LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(j.company) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Job> searchJobs(@Param("keyword") String keyword);
}
