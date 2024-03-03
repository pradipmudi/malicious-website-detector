package com.maliciouswebsitedetector.entity;

import jakarta.persistence.*;

@Entity
@IdClass(BloomFilterKey.class)
public class BloomFilterEntity {

    @Id
    private int size;

    @Id
    private int hashCount;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] bitArray;

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

    public byte[] getBitArray() {
        return bitArray;
    }

    public void setBitArray(byte[] bitArray) {
        this.bitArray = bitArray;
    }
}
