package com.portfolio.repository;

import com.portfolio.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByIsFeaturedTrueOrderBySortOrderAsc();
    
    Page<Project> findByStatusOrderByCreatedAtDesc(Project.Status status, Pageable pageable);
    
    @Query("SELECT p FROM Project p WHERE " +
           "(:status IS NULL OR p.status = :status) AND " +
           "(:technology IS NULL OR :technology MEMBER OF p.technologies) AND " +
           "(LOWER(p.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Project> findProjectsWithFilters(
        @Param("status") Project.Status status,
        @Param("technology") String technology,
        @Param("search") String search,
        Pageable pageable
    );
    
    @Query("SELECT DISTINCT t FROM Project p JOIN p.technologies t ORDER BY t")
    List<String> findAllTechnologies();
}