package com.portfolio.controller;

import com.portfolio.dto.CertificateDto;
import com.portfolio.service.CertificateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @GetMapping
    public ResponseEntity<List<CertificateDto>> getAllCertificates() {
        List<CertificateDto> certificates = certificateService.getAllCertificates();
        return ResponseEntity.ok(certificates);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CertificateDto>> getCertificatesWithFilters(
            @RequestParam(required = false) String issuer,
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "issueDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Page<CertificateDto> certificates = certificateService.getCertificatesWithFilters(
                issuer, search, page, size, sortBy, sortDir);
        return ResponseEntity.ok(certificates);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<CertificateDto>> getFeaturedCertificates() {
        List<CertificateDto> certificates = certificateService.getFeaturedCertificates();
        return ResponseEntity.ok(certificates);
    }

    @GetMapping("/issuers")
    public ResponseEntity<List<String>> getAllIssuers() {
        List<String> issuers = certificateService.getAllIssuers();
        return ResponseEntity.ok(issuers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificateDto> getCertificateById(@PathVariable Long id) {
        return certificateService.getCertificateById(id)
                .map(certificate -> ResponseEntity.ok().body(certificate))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CertificateDto> createCertificate(@Valid @RequestBody CertificateDto certificateDto) {
        CertificateDto createdCertificate = certificateService.createCertificate(certificateDto);
        return ResponseEntity.ok(createdCertificate);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CertificateDto> updateCertificate(@PathVariable Long id, @Valid @RequestBody CertificateDto certificateDto) {
        return certificateService.updateCertificate(id, certificateDto)
                .map(certificate -> ResponseEntity.ok().body(certificate))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCertificate(@PathVariable Long id) {
        if (certificateService.deleteCertificate(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}


