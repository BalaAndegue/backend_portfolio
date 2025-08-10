package com.portfolio.service;

import com.portfolio.dto.ProfileDto;
import com.portfolio.entity.Profile;
import com.portfolio.repository.ProfileRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Optional<ProfileDto> getProfile() {
        // Assuming there's only one profile record
        return profileRepository.findAll().stream()
                .findFirst()
                .map(profile -> modelMapper.map(profile, ProfileDto.class));
    }

    public ProfileDto createOrUpdateProfile(ProfileDto profileDto) {
        Profile profile;
        
        if (profileDto.getId() != null) {
            // Update existing profile
            profile = profileRepository.findById(profileDto.getId())
                    .orElse(new Profile());
        } else {
            // Check if profile already exists (assuming single profile)
            profile = profileRepository.findAll().stream()
                    .findFirst()
                    .orElse(new Profile());
        }
        
        modelMapper.map(profileDto, profile);
        Profile savedProfile = profileRepository.save(profile);
        return modelMapper.map(savedProfile, ProfileDto.class);
    }
}