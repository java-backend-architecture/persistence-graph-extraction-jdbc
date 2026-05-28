CREATE TABLE owners (
    id   VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE pets (
    id        VARCHAR(36) PRIMARY KEY,
    name      VARCHAR(100) NOT NULL,
    owner_id  VARCHAR(36) NOT NULL REFERENCES owners(id)
);

CREATE TABLE visits (
    id      VARCHAR(36) PRIMARY KEY,
    date    DATE NOT NULL,
    pet_id  VARCHAR(36) NOT NULL REFERENCES pets(id)
);