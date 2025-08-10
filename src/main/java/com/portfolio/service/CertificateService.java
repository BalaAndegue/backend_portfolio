package com.portfolio.service;

import com.portfolio.dto.CertificateDto;
import com.portfolio.entity.Certificate;
import com.portfolio.repository.CertificateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CertificateService {

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<CertificateDto> getAllCertificates() {
        return certificateRepository.findAll(Sort.by(Sort.Direction.DESC, "issueDate"))
                .stream()
                .map(certificate -> modelMapper.map(certificate, CertificateDto.class))
                .collect(Collectors.toList());
    }

    public Page<CertificateDto> getCertificatesWithFilters(String issuer, String search, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : 
            Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Certificate> certificates = certificateRepository.findCertificatesWithFilters(issuer, search, pageable);
        
        return certificates.map(certificate -> modelMapper.map(certificate, CertificateDto.class));
    }

    public List<CertificateDto> getFeaturedCertificates() {
        return certificateRepository.findByIsFeaturedTrueOrderBySortOrderAsc()
                .stream()
                .map(certificate -> modelMapper.map(certificate, CertificateDto.class))
                .collect(Collectors.toList());
    }

    public Optional<CertificateDto> getCertificateById(Long id) {
        return certificateRepository.findById(id)
                .map(certificate -> modelMapper.map(certificate, CertificateDto.class));
    }

    public CertificateDto createCertificate(CertificateDto certificateDto) {
        Certificate certificate = modelMapper.map(certificateDto, Certificate.class);
        certificate.setId(null); // Ensure it's a new entity
        Certificate savedCertificate = certificateRepository.save(certificate);
        return modelMapper.map(savedCertificate, CertificateDto.class);
    }

    public Optional<CertificateDto> updateCertificate(Long id, CertificateDto certificateDto) {
        return certificateRepository.findById(id)
                .map(existingCertificate -> {
                    modelMapper.map(certificateDto, existingCertificate);
                    existingCertificate.setId(id); // Ensure ID is preserved
                    Certificate updatedCertificate = certificateRepository.save(existingCertificate);
                    return modelMapper.map(updatedCertificate, CertificateDto.class);
                });
    }

    public boolean deleteCertificate(Long id) {
        if (certificateRepository.existsById(id)) {
            certificateRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<String> getAllIssuers() {
        return certificateRepository.findAllIssuers();
    }
}