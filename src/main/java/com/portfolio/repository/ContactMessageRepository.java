package com.portfolio.repository;

import com.portfolio.entity.ContactMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
    Page<ContactMessage> findByIsReadOrderByCreatedAtDesc(Boolean isRead, Pageable pageable);
    Long countByIsRead(Boolean isRead);
}