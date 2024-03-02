package com.maliciouswebsitedetector.controller;

import com.maliciouswebsitedetector.service.BloomFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bloomfilter")
public class MaliciousWebsiteDetector {
    private final BloomFilterService bloomFilterService;

    // Injecting the service dependency using constructor injection
    @Autowired
    public MaliciousWebsiteDetector(BloomFilterService bloomFilterService) {
        this.bloomFilterService = bloomFilterService;
    }

    // Endpoint to add a website to the Bloom Filter
    @PostMapping("/add")
    public void addWebsite(@RequestBody String website) {
        bloomFilterService.add(website);
    }

    // Endpoint to check if a website is likely malicious
    @GetMapping("/check")
    public boolean checkMalicious(@RequestParam String website) {
        return bloomFilterService.contains(website);
    }
}

