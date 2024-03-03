package com.maliciouswebsitedetector.entity;

import java.io.Serializable;
import java.util.Objects;

public class BloomFilterKey implements Serializable {

    private int size;

    private int hashCount;

    public BloomFilterKey() {
        // Default constructor required by Hibernate
    }

    public BloomFilterKey(int size, int hashCount) {
        this.size = size;
        this.hashCount = hashCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getHashCount() {
        return hashCount;
    }

    public void setHashCount(int hashCount) {
        this.hashCount = hashCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BloomFilterKey)) return false;
        BloomFilterKey that = (BloomFilterKey) o;
        return size == that.size && hashCount == that.hashCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, hashCount);
    }
}

