CREATE TABLE bloom_filter_entity (
    size INT NOT NULL,
    hash_count INT NOT NULL,
    bit_array BLOB,
    PRIMARY KEY (size, hash_count)
);
