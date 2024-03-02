package com.maliciouswebsitedetector.entity;

import jakarta.persistence.*;


@Entity
public class BloomFilterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "BLOB")
    private byte[] bitArray;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getBitArray() {
        return bitArray;
    }

    public void setBitArray(byte[] bitArray) {
        this.bitArray = bitArray;
    }
}

