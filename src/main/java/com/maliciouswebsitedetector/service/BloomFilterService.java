package com.maliciouswebsitedetector.service;

import com.maliciouswebsitedetector.entity.BloomFilterEntity;
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
    private int size;
    private final int hashCount;
    private final BloomFilterRepository bloomFilterRepository;

    @Autowired
    public BloomFilterService(BloomFilterRepository bloomFilterRepository) {
        this.bloomFilterRepository = bloomFilterRepository;
        hashCount = 5;
        loadFromDatabase(); // Load Bloom Filter data from the database on application startup
    }

    private void loadFromDatabase() {
        Optional<BloomFilterEntity> optionalEntity = bloomFilterRepository.findById(1L);
        if (optionalEntity.isPresent()) {
            BloomFilterEntity bloomFilterEntity = optionalEntity.get();
            this.bitArray = BitSet.valueOf(bloomFilterEntity.getBitArray());
            this.size = this.bitArray.size();
        } else {
            // Initialize with default values if no data found in the database
            this.bitArray = new BitSet(1000); // Default size
            this.size = 1000;
        }
    }

    @Transactional
    public void saveToDatabase() {
        byte[] bitArrayBytes = bitArray.toByteArray();
        BloomFilterEntity bloomFilterEntity = new BloomFilterEntity();
        bloomFilterEntity.setId(1L); // Fixed ID for simplicity
        bloomFilterEntity.setBitArray(bitArrayBytes);
        bloomFilterRepository.save(bloomFilterEntity);
    }

    public synchronized void add(String website) {
        for (int ii = 0; ii < this.hashCount; ii++) {
            int index = Math.abs(website.hashCode() * ii) % this.size;
            bitArray.set(index, true);
        }
        saveToDatabase(); // Save Bloom Filter data to the database after each addition
    }

    public boolean contains(String website) {
        for (int ii = 0; ii < this.hashCount; ii++) {
            int index = Math.abs(website.hashCode() * ii) % this.size;
            if (!bitArray.get(index)) {
                return false;
            }
        }
        return true;
    }
}
