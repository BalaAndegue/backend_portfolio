package com.portfolio.repository;

import com.portfolio.entity.Certificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    List<Certificate> findByIsFeaturedTrueOrderBySortOrderAsc();
    
    @Query("SELECT c FROM Certificate c WHERE " +
           "(:issuer IS NULL OR LOWER(c.issuer) LIKE LOWER(CONCAT('%', :issuer, '%'))) AND " +
           "(LOWER(c.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.description) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Certificate> findCertificatesWithFilters(
        @Param("issuer") String issuer,
        @Param("search") String search,
        Pageable pageable
    );
    
    @Query("SELECT DISTINCT c.issuer FROM Certificate c ORDER BY c.issuer")
    List<String> findAllIssuers();
}