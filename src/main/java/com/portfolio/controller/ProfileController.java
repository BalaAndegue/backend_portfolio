package com.portfolio.controller;

import com.portfolio.dto.ProfileDto;
import com.portfolio.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileDto> getProfile() {
        return profileService.getProfile()
                .map(profile -> ResponseEntity.ok().body(profile))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfileDto> createOrUpdateProfile(@Valid @RequestBody ProfileDto profileDto) {
        ProfileDto savedProfile = profileService.createOrUpdateProfile(profileDto);
        return ResponseEntity.ok(savedProfile);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfileDto> updateProfile(@Valid @RequestBody ProfileDto profileDto) {
        ProfileDto updatedProfile = profileService.createOrUpdateProfile(profileDto);
        return ResponseEntity.ok(updatedProfile);
    }
}