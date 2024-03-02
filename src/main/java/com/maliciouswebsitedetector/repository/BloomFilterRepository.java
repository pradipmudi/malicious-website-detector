package com.maliciouswebsitedetector.repository;

import com.maliciouswebsitedetector.entity.BloomFilterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloomFilterRepository extends JpaRepository<BloomFilterEntity, Long> {
}

