package com.maliciouswebsitedetector.controller;

import com.maliciouswebsitedetector.service.BloomFilterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bloomfilter")
public class MaliciousWebsiteDetector {
    private final BloomFilterService bloomFilterService;

    // Injecting the service dependency using constructor injection
    public MaliciousWebsiteDetector(BloomFilterService bloomFilterService) {
        this.bloomFilterService = bloomFilterService;
    }

    // Endpoint to add a malicious website to the Bloom Filter
    @PostMapping("/add")
    public ResponseEntity<String> addWebsite(@RequestBody String website) {
        bloomFilterService.add(website);
        return ResponseEntity.status(HttpStatus.CREATED).body("Website added to the Bloom Filter successfully.");
    }

    // Endpoint to check if a website is likely malicious
    @GetMapping("/check")
    public ResponseEntity<String> checkMalicious(@RequestParam String website) {
        boolean isMalicious = bloomFilterService.contains(website);
        if (isMalicious) {
            return ResponseEntity.ok("The website '" + website + "' is likely malicious.");
        } else {
            return ResponseEntity.ok("The website '" + website + "' is not malicious.");
        }
    }
}
