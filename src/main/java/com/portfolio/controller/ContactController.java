package com.portfolio.controller;

import com.portfolio.dto.ContactMessageDto;
import com.portfolio.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<ContactMessageDto> sendMessage(@Valid @RequestBody ContactMessageDto contactMessageDto) {
        ContactMessageDto savedMessage = contactService.saveContactMessage(contactMessageDto);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/messages")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ContactMessageDto>> getAllMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ContactMessageDto> messages = contactService.getAllMessages(page, size);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/messages/unread")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<ContactMessageDto>> getUnreadMessages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<ContactMessageDto> messages = contactService.getMessagesByReadStatus(false, page, size);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/messages/count/unread")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Long>> getUnreadCount() {
        Long count = contactService.getUnreadCount();
        return ResponseEntity.ok(Map.of("count", count));
    }

    @GetMapping("/messages/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContactMessageDto> getMessageById(@PathVariable Long id) {
        return contactService.getMessageById(id)
                .map(message -> ResponseEntity.ok().body(message))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/messages/{id}/read")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContactMessageDto> markAsRead(@PathVariable Long id) {
        return contactService.markAsRead(id)
                .map(message -> ResponseEntity.ok().body(message))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/messages/{id}/replied")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContactMessageDto> markAsReplied(@PathVariable Long id) {
        return contactService.markAsReplied(id)
                .map(message -> ResponseEntity.ok().body(message))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/messages/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteMessage(@PathVariable Long id) {
        if (contactService.deleteMessage(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}