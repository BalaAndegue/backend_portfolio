package com.portfolio.service;

import com.portfolio.dto.ContactMessageDto;
import com.portfolio.entity.ContactMessage;
import com.portfolio.repository.ContactMessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JavaMailSender mailSender;

    public ContactMessageDto saveContactMessage(ContactMessageDto contactMessageDto) {
        ContactMessage contactMessage = modelMapper.map(contactMessageDto, ContactMessage.class);
        contactMessage.setId(null); // Ensure it's a new entity
        ContactMessage savedMessage = contactMessageRepository.save(contactMessage);
        
        // Send notification email (optional)
        sendNotificationEmail(savedMessage);
        
        return modelMapper.map(savedMessage, ContactMessageDto.class);
    }

    public Page<ContactMessageDto> getAllMessages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ContactMessage> messages = contactMessageRepository.findAll(pageable);
        return messages.map(message -> modelMapper.map(message, ContactMessageDto.class));
    }

    public Page<ContactMessageDto> getMessagesByReadStatus(Boolean isRead, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ContactMessage> messages = contactMessageRepository.findByIsReadOrderByCreatedAtDesc(isRead, pageable);
        return messages.map(message -> modelMapper.map(message, ContactMessageDto.class));
    }

    public Optional<ContactMessageDto> getMessageById(Long id) {
        return contactMessageRepository.findById(id)
                .map(message -> modelMapper.map(message, ContactMessageDto.class));
    }

    public Optional<ContactMessageDto> markAsRead(Long id) {
        return contactMessageRepository.findById(id)
                .map(message -> {
                    message.setIsRead(true);
                    ContactMessage updatedMessage = contactMessageRepository.save(message);
                    return modelMapper.map(updatedMessage, ContactMessageDto.class);
                });
    }

    public Optional<ContactMessageDto> markAsReplied(Long id) {
        return contactMessageRepository.findById(id)
                .map(message -> {
                    message.setIsRead(true);
                    message.setRepliedAt(LocalDateTime.now());
                    ContactMessage updatedMessage = contactMessageRepository.save(message);
                    return modelMapper.map(updatedMessage, ContactMessageDto.class);
                });
    }

    public boolean deleteMessage(Long id) {
        if (contactMessageRepository.existsById(id)) {
            contactMessageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Long getUnreadCount() {
        return contactMessageRepository.countByIsRead(false);
    }

    private void sendNotificationEmail(ContactMessage message) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo("balaandeguefrancoislionnel@gmail.com"); // Replace with your email
            email.setSubject("New Contact Message from Portfolio");
            email.setText(String.format(
                "New message received:\n\n" +
                "Name: %s\n" +
                "Email: %s\n" +
                "Subject: %s\n" +
                "Message: %s\n\n" +
                "Received at: %s",
                message.getName(),
                message.getEmail(),
                message.getSubject(),
                message.getMessage(),
                message.getCreatedAt()
            ));
            
            mailSender.send(email);
        } catch (Exception e) {
            // Log error but don't fail the request
            System.err.println("Failed to send notification email: " + e.getMessage());
        }
    }
}