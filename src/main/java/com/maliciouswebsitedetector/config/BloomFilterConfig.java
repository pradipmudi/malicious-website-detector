package com.maliciouswebsitedetector.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BloomFilterConfig {

    @Value("${bloomfilter.size}")
    private int size;

    @Value("${bloomfilter.hashcount}")
    private int hashCount;

    public int getSize() {
        return size;
    }

    public int getHashCount() {
        return hashCount;
    }
}

