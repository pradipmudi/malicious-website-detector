package com.maliciouswebsitedetector.service;

import com.maliciouswebsitedetector.config.BloomFilterConfig;
import com.maliciouswebsitedetector.entity.BloomFilterEntity;
import com.maliciouswebsitedetector.entity.BloomFilterKey;
import com.maliciouswebsitedetector.repository.BloomFilterRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.BitSet;
import java.util.Optional;

@Service
public class BloomFilterService {
    /*
     *
     * BitSet is used instead of a simple bit array because it provides a memory-efficient
     * way to represent a large array of bits. It internally uses a long array to store the
     * bits and provides methods for setting, clearing, and checking individual bits. This
     * abstraction allows for more efficient memory usage and simpler manipulation of the bits.
     *
     */
    private BitSet bitArray; // BitSet to represent the Bloom Filter efficiently
    private int bloomFilterSize;
    private int hashCount;
    private final BloomFilterRepository bloomFilterRepository;
    private final BloomFilterConfig bloomFilterConfig;

    @Autowired
    public BloomFilterService(BloomFilterRepository bloomFilterRepository, BloomFilterConfig bloomFilterConfig) {
        this.bloomFilterRepository = bloomFilterRepository;
        this.bloomFilterConfig = bloomFilterConfig;
        this.hashCount = bloomFilterConfig.getHashCount();
        this.bloomFilterSize = bloomFilterConfig.getSize();
        loadFromDatabase(); // Load Bloom Filter data from the database on application startup
    }

    private void loadFromDatabase() {
        Optional<BloomFilterEntity> optionalEntity = bloomFilterRepository.findById(new BloomFilterKey(bloomFilterSize, hashCount));
        if (optionalEntity.isPresent()) {
            BloomFilterEntity bloomFilterEntity = optionalEntity.get();
            this.bitArray = BitSet.valueOf(bloomFilterEntity.getBitArray());
            this.bloomFilterSize = bloomFilterEntity.getSize();
            this.hashCount = bloomFilterEntity.getHashCount();
        } else {
            // Initialize with default values if no data found in the database
            this.bitArray = new BitSet(bloomFilterSize); // Default size
        }
    }

    @Transactional
    public void saveToDatabase() {
        byte[] bitArrayBytes = bitArray.toByteArray();
        BloomFilterEntity bloomFilterEntity = new BloomFilterEntity();
        bloomFilterEntity.setSize(bloomFilterSize);
        bloomFilterEntity.setHashCount(hashCount);// Fixed ID for simplicity
        bloomFilterEntity.setBitArray(bitArrayBytes);
        bloomFilterRepository.save(bloomFilterEntity);
    }

    public synchronized void add(String website) {
        for (int hash = 0; hash < this.hashCount; hash++) {
            int index = Math.abs(website.hashCode() * hash) % this.bloomFilterSize;
            bitArray.set(index, true);
        }
        saveToDatabase(); // Save Bloom Filter data to the database after each addition
    }

    public boolean contains(String website) {
        for (int hash = 0; hash < this.hashCount; hash++) {
            int index = Math.abs(website.hashCode() * hash) % this.bloomFilterSize;
            if (!bitArray.get(index)) {
                return false;
            }
        }
        return true;
    }
}
