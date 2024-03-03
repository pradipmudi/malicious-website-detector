package com.maliciouswebsitedetector.repository;

import com.maliciouswebsitedetector.entity.BloomFilterEntity;
import com.maliciouswebsitedetector.entity.BloomFilterKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloomFilterRepository extends JpaRepository<BloomFilterEntity, BloomFilterKey> {
}

